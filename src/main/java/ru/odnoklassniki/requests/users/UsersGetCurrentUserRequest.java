package ru.odnoklassniki.requests.users;

import ru.odnoklassniki.common.ApiMethod;
import ru.odnoklassniki.requests.ApiRequest;
import ru.odnoklassniki.common.users.UserInfoField;
import ru.odnoklassniki.requests.ApiRequestConstants;

/**
 * Created by Maksim Egorichev on 4/23/18 at 9:05 AM
 *
 * Запрос на выполнение метода users.getCurrentUser
 * Документация: https://apiok.ru/dev/methods/rest/users/users.getCurrentUser
 */
public class UsersGetCurrentUserRequest extends ApiRequest {

    public UsersGetCurrentUserRequest() {
        this.setApiMethod(ApiMethod.USERS_GET_CURRENT_USER);
    }

    public UsersGetCurrentUserRequest(UserInfoField[] userInfoFields) {
        this.setApiMethod(ApiMethod.USERS_GET_CURRENT_USER).addParam(ApiRequestConstants.FIELDS_PARAM, userInfoFields);
    }
}
