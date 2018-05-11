package ru.odnoklassniki.requests.group;

import ru.odnoklassniki.common.ApiMethod;
import ru.odnoklassniki.requests.ApiRequest;
import ru.odnoklassniki.requests.ApiRequestConstants;

/**
 * Created by Maksim Egorichev on 5/9/18 at 4:30 PM
 */
public class GroupGetUserGroupsV2Request extends ApiRequest {

    public GroupGetUserGroupsV2Request() {
        this.setApiMethod(ApiMethod.GROUP_GET_USER_GROUPS_V2);
    }

    public GroupGetUserGroupsV2Request(Integer count) {
        this.setApiMethod(ApiMethod.GROUP_GET_USER_GROUPS_V2).addParam(ApiRequestConstants.COUNT_PARAM, count);
    }

    public GroupGetUserGroupsV2Request(String uid) {
        this.setApiMethod(ApiMethod.GROUP_GET_USER_GROUPS_V2).addParam(ApiRequestConstants.UID_PARAM, uid);
    }
}
