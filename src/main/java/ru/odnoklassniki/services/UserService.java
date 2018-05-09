package ru.odnoklassniki.services;

import ru.odnoklassniki.ApiClient;
import ru.odnoklassniki.common.ApiException;
import ru.odnoklassniki.common.users.UserInfoField;
import ru.odnoklassniki.requests.users.UsersGetCurrentUserRequest;
import ru.odnoklassniki.requests.users.UsersGetInfoByRequest;
import ru.odnoklassniki.responses.users.UsersGetCurrentUserResponse;
import ru.odnoklassniki.responses.users.UsersGetInfoByResponse;

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
    public UsersGetCurrentUserResponse getCurrentUser(UserInfoField[] userInfoFields) throws ApiException {
        UsersGetCurrentUserRequest getCurrentUserRequest = new UsersGetCurrentUserRequest(userInfoFields);
        return apiClient.executeRequest(getCurrentUserRequest, UsersGetCurrentUserResponse.class);
    }

    /**
     * Получение информации о текущем пользователе с дефолтным набором полей в ответе
     */
    public UsersGetCurrentUserResponse getCurrentUser() throws ApiException {
        UsersGetCurrentUserRequest getCurrentUserRequest = new UsersGetCurrentUserRequest();
        return apiClient.executeRequest(getCurrentUserRequest, UsersGetCurrentUserResponse.class);
    }

    public UsersGetInfoByResponse getInfoBy(String uid, UserInfoField[] userInfoFields) throws ApiException {
        UsersGetInfoByRequest usersGetInfoByRequest = new UsersGetInfoByRequest(uid, userInfoFields);
        return apiClient.executeRequest(usersGetInfoByRequest, UsersGetInfoByResponse.class);
    }

    public UsersGetInfoByResponse getInfoBy(String uid, UserInfoField[] userInfoFields, Boolean registerAsGuest) throws ApiException {
        UsersGetInfoByRequest usersGetInfoByRequest = new UsersGetInfoByRequest(uid, userInfoFields, registerAsGuest);
        return apiClient.executeRequest(usersGetInfoByRequest, UsersGetInfoByResponse.class);
    }
}
