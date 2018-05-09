package ru.odnoklassniki.requests.users;

import ru.odnoklassniki.common.ApiMethod;
import ru.odnoklassniki.common.users.UserInfoField;
import ru.odnoklassniki.requests.ApiRequest;
import ru.odnoklassniki.requests.ApiRequestConstants;

/**
 * Created by Maksim Egorichev on 5/9/18 at 4:43 PM
 */
public class UsersGetInfoByRequest extends ApiRequest {

    public UsersGetInfoByRequest(String uid, UserInfoField[] fields) {
        this.setApiMethod(ApiMethod.USERS_GET_INFO_BY)
                .addParam(ApiRequestConstants.UID_PARAM, uid)
                .addParam(ApiRequestConstants.FIELDS_PARAM, fields);
    }

    public UsersGetInfoByRequest(String uid, UserInfoField[] fields, Boolean registerAsGuest) {
        this.setApiMethod(ApiMethod.USERS_GET_INFO_BY)
                .addParam(ApiRequestConstants.UID_PARAM, uid)
                .addParam(ApiRequestConstants.FIELDS_PARAM, fields)
                .addParam(ApiRequestConstants.REGISTER_AS_GUEST_PARAM, registerAsGuest);
    }
}
