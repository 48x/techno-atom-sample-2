package ru.odnoklassniki.services;

import ru.odnoklassniki.ApiClient;
import ru.odnoklassniki.common.ApiException;
import ru.odnoklassniki.common.users.UserInfoField;
import ru.odnoklassniki.requests.users.GetCurrentUserRequest;
import ru.odnoklassniki.responses.users.GetCurrentUserResponse;

/**
 * Created by Maksim Egorichev on 4/23/18 at 9:20 AM
 *
 * Сервис для выполнения методов группы users: https://apiok.ru/dev/methods/rest/users/
 */
public class UserService {
    private ApiClient apiClient;

    UserService(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Получение информации о текущем пользователе
     * @param userInfoFields    список полей, которые надо получить в ответе
     */
    public GetCurrentUserResponse getCurrentUser(UserInfoField[] userInfoFields) throws ApiException {
        GetCurrentUserRequest getCurrentUserRequest = new GetCurrentUserRequest(userInfoFields);
        return apiClient.executeRequest(getCurrentUserRequest, GetCurrentUserResponse.class);
    }

    /**
     * Получение информации о текущем пользователе с дефолтным набором полей в ответе
     */
    public GetCurrentUserResponse getCurrentUser() throws ApiException {
        GetCurrentUserRequest getCurrentUserRequest = new GetCurrentUserRequest();
        return apiClient.executeRequest(getCurrentUserRequest, GetCurrentUserResponse.class);
    }
}
