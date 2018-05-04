package ru.odnoklassniki;

import org.apache.http.NameValuePair;

import java.security.MessageDigest;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Maksim Egorichev on 4/23/18 at 11:47 AM
 *
 * Класс для вычисления подписи запроса к API
 */
public final class RequestSigner {
    private static final String CHARSET = "UTF-8";
    private static final String DIGEST_TYPE = "MD5";
    private static final String PARAM_DELIMITER = "=";

    private RequestSigner() {
    }

    /**
     * Вычисление подписи запроса
     * @param params        список параметров запроса
     * @param secretKey     секретный клюс сессии / приложения
     * @return              подпись запроса
     */
    public static String generateSignature(List<NameValuePair> params, String secretKey) {
        return md5(getSignatureSource(params, secretKey));
    }

    public static String md5(String source) {
        try {
            byte[] bytes = source.getBytes(CHARSET);
            MessageDigest md = MessageDigest
                    .getInstance(DIGEST_TYPE);
            byte[] digest = md.digest(bytes);
            return toHex(digest);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Подсчет строкового представления параметров запроса для вычисления подписи
     * @param params        список параметров запроса
     * @param secretKey     секретный ключ сессии / приложения
     * @return              строковое представления параметров запроса
     */
    private static String getSignatureSource(List<NameValuePair> params, String secretKey) {
        List<NameValuePair> sortedParams = params
                .stream()
                .sorted(Comparator.comparing(NameValuePair::getName))
                .collect(Collectors.toList());

        StringBuilder str = new StringBuilder();
        for (NameValuePair param : sortedParams) {
                str.append(param.getName()).append(PARAM_DELIMITER).append(param.getValue());
        }
        str.append(secretKey);
        return str.toString();
    }

    private static String toHex(byte[] digest) {
        StringBuilder result = new StringBuilder(32);
        for (byte b : digest) {
            result.append(Integer.toHexString((b & 0xf0) >> 4));
            result.append(Integer.toHexString(b & 0x0f));
        }
        return result.toString();
    }
}
