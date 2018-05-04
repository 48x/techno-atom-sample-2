package ru.odnoklassniki.requests;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import ru.odnoklassniki.common.ApiMethod;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Maksim Egorichev on 4/23/18 at 09:07 AM
 *
 * Запрос к API
 */
public class ApiRequest {

    private List<NameValuePair> params = new ArrayList<>();

    /**
     * Установка API-метода запроса
     * @param apiMethod API-метод запроса
     */
    public ApiRequest setApiMethod(ApiMethod apiMethod) {
        this.params.add(new BasicNameValuePair(ApiRequestConstants.METHOD_PARAM, apiMethod.getMethodGroup() + "." + apiMethod.getMethodName()));
        return this;
    }

    /**
     * Установка входного параметра запроса
     * @param param пара название-значение входного параметра
     */
    public ApiRequest addParam(NameValuePair param) {
        if (param != null) {
            this.params.add(param);
        }
        return this;
    }

    /**
     * Установка входного параметра запроса
     * @param paramName     название параметра
     * @param paramValue    значение параметра
     */
    public ApiRequest addParam(String paramName, Object paramValue) {
        if (paramValue == null) {
            return this;
        }
        this.params.add(new BasicNameValuePair(paramName, paramValue.toString()));
        return this;
    }

    /**
     * Установка входного параметра запроса
     * @param paramName     название параметра
     * @param paramValue    значение параметра [массив]
     */
    public ApiRequest addParam(String paramName, Object[] paramValue) {
        if (paramValue == null) {
            return this;
        }
        this.params.add(new BasicNameValuePair(
                paramName,
                Arrays.toString(paramValue).replace("[", "").replace("]", ""))
        );
        return this;
    }

    /**
     * Получение списка параметров запроса
     * @return  список параметров запроса
     */
    public List<NameValuePair> getParams() {
        return params;
    }
}
