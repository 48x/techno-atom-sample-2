package ru.odnoklassniki;

import com.google.common.base.Throwables;
import com.google.gson.Gson;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.SocketConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import ru.odnoklassniki.common.ApiErrorInfo;
import ru.odnoklassniki.common.ApiException;
import ru.odnoklassniki.requests.ApiRequest;
import ru.odnoklassniki.requests.ApiRequestConstants;
import ru.odnoklassniki.responses.ApiResponse;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

/**
 * Created by Maksim Egorichev on 4/23/18 at 8:47 AM
 *
 * Клиент к API ok.ru для подписи и выполнения запросов
 */
public class ApiClient {
    private static final Gson GSON = new Gson();
    private static final String FORMAT = "JSON";

    private String apiSecretKey;
    private String apiPublicKey;
    private String apiServer;
    private int timeout;

    private String accessToken;
    private String sessionSecretKey;

    public ApiClient(String apiSecretKey, String apiPublicKey, String apiServer, int timeout) {
        this.apiSecretKey = apiSecretKey;
        this.apiPublicKey = apiPublicKey;
        this.apiServer = apiServer;
        this.timeout = timeout;
    }

    /**
     * Привязка пользовательской сессии к клиенту
     * @param accessToken       access_token пользователя
     * @param sessionSecretKey  session_secret_key пользователя
     */
    public void bindSession(String accessToken, String sessionSecretKey) {
        this.accessToken = accessToken;
        this.sessionSecretKey = sessionSecretKey;
    }

    /**
     * Отвязка пользовательской сессии от клиента
     */
    public void dropSession() {
        this.accessToken = null;
        this.sessionSecretKey = null;
    }

    /**
     * Проверка наличия привязанной сессии
     * @return  флаг наличия привязанной сессии
     */
    private boolean isSessionActive() {
        return (this.accessToken != null && this.sessionSecretKey != null);
    }

    /**
     * Выполнение запроса в API
     * @param apiRequest        запрос в API
     * @param responseClass     класс ответа на выполнение запроса
     * @return
     */
    public <R extends ApiRequest, T> T executeRequest(R apiRequest, Class<T> responseClass) throws ApiException {
        final String rawResponse = getRawResponse(apiRequest);
        return convertResponse(rawResponse,responseClass);
    }

    /**
     * Подпись запроса к API
     * @param apiRequest    запрос без подписи
     */
    private <R extends ApiRequest> void signRequest(R apiRequest) {
        apiRequest.addParam(
                new BasicNameValuePair(
                        ApiRequestConstants.SIG_PARAM,
                        RequestSigner.generateSignature(
                                apiRequest.getParams(),
                                isSessionActive() ? sessionSecretKey : RequestSigner.md5(accessToken + apiSecretKey).toLowerCase()
                        )
                )
        );

        apiRequest.addParam(new BasicNameValuePair(ApiRequestConstants.ACCESS_TOKEN_PARAM, accessToken));
    }

    /**
     * Выполнение запроса и получение строкового представления ответа
     * @param apiRequest        запрос в API
     * @return                  строковое представление ответа от API
     */
    private <R extends ApiRequest> String getRawResponse(R apiRequest) throws ApiException {
        CloseableHttpClient httpClient = getHttpClient(timeout);

        addEssentialParams(apiRequest);
        signRequest(apiRequest);

        CloseableHttpResponse httpResponse = null;
        try {
            HttpPost httpPost = new HttpPost(apiServer);
            httpPost.setEntity(new UrlEncodedFormEntity(apiRequest.getParams()));
            httpResponse = httpClient.execute(httpPost);

        } catch (IOException e) {
            Throwables.propagate(e);
        }
        return validateResponse(httpResponse);
    }

    private <T> T convertResponse(String rawResponse, Class<T> responseClass) {
        return GSON.fromJson(rawResponse, responseClass);
    }

    /**
     * Валидация ответа от API
     * @param httpResponse  ответ от API
     * @return              строковое представление ответа от API
     */
    private String validateResponse(HttpResponse httpResponse) throws ApiException {
        final Optional<Header> invocationErrorHeader = Arrays.stream(httpResponse.getAllHeaders()).filter(h -> h.getName().equalsIgnoreCase("invocation-error")).findFirst();
        String responseAsString = null;

        try {
            responseAsString = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
        } catch (IOException e) {
            Throwables.propagate(e);
        }

        if (httpResponse.getStatusLine().getStatusCode() != 200 || invocationErrorHeader.isPresent()) {
            ApiException apiException = new ApiException();
            apiException.setHeaders(httpResponse.getAllHeaders());
            apiException.setApiErrorInfo(GSON.fromJson(responseAsString, ApiErrorInfo.class));
            throw apiException;
        }

        return responseAsString;
    }

    /**
     * Добавление обязательных параметров в список параметров запроса, привязанных к API-приложению
     * @param apiRequest    запрос к апи
     */
    private <R extends ApiRequest> void addEssentialParams(R apiRequest) {
        apiRequest.addParam(ApiRequestConstants.APP_KEY_PARAM, apiPublicKey);
        apiRequest.addParam(ApiRequestConstants.FORMAT_PARAM, FORMAT);
    }

    /**
     * Метод для создания http-клиента
     * @param timeout   максимальный таймаут при выполнении запроса
     * @return          http-клиент
     */
    private static CloseableHttpClient getHttpClient(int timeout) {
        RequestConfig requestConfig = RequestConfig
                .custom()
                .setConnectionRequestTimeout(timeout)
                .setConnectTimeout(timeout)
                .setSocketTimeout(timeout)
                .build();

        SocketConfig socketConfig = SocketConfig
                .custom()
                .setSoTimeout(timeout)
                .build();

        return HttpClientBuilder
                .create()
                .setDefaultRequestConfig(requestConfig)
                .setDefaultSocketConfig(socketConfig)
                .build();
    }

    @Override
    public String toString() {
        return "ApiClient{" +
                "apiSecretKey='" + apiSecretKey + '\'' +
                ", apiPublicKey='" + apiPublicKey + '\'' +
                ", apiServer='" + apiServer + '\'' +
                ", timeout=" + timeout +
                ", accessToken='" + accessToken + '\'' +
                ", sessionSecretKey='" + sessionSecretKey + '\'' +
                '}';
    }
}
