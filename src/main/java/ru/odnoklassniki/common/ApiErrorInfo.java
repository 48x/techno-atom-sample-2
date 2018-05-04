package ru.odnoklassniki.common;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Maksim Egorichev on 4/23/18 at 11:13 AM
 *
 * Описание ошибки, произошедшей при выполнении запроса
 */
public class ApiErrorInfo {

    @SerializedName("error_code")
    private Integer errorCode;

    @SerializedName("error_msg")
    private String errorMessage;

    @SerializedName("error_data")
    private String errorData;

    public ApiErrorInfo(Integer errorCode, String errorMessage, String errorData) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.errorData = errorData;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public String getErrorData() {
        return errorData;
    }

    @Override
    public String toString() {
        return "ApiErrorInfo{" +
                "errorCode=" + errorCode +
                ", errorMessage='" + errorMessage + '\'' +
                ", errorData='" + errorData + '\'' +
                '}';
    }
}
