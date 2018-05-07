package ru.odnoklassniki.requests.group;

import ru.odnoklassniki.common.ApiMethod;
import ru.odnoklassniki.common.group.PagingDirection;
import ru.odnoklassniki.requests.ApiRequest;
import ru.odnoklassniki.requests.ApiRequestConstants;

public class GetUserGroupsRequest extends ApiRequest {

    public GetUserGroupsRequest() {
        this.setApiMethod(ApiMethod.GET_USER_GROUPS);
    }

    public GetUserGroupsRequest(String anchor) {
        this.setApiMethod(ApiMethod.GET_USER_GROUPS)
                .addParam(ApiRequestConstants.ANCHOR_PARAM, anchor);
    }

    public GetUserGroupsRequest(PagingDirection pagingDirection) {
        this.setApiMethod(ApiMethod.GET_USER_GROUPS)
                .addParam(ApiRequestConstants.DIRECTION_PARAM, pagingDirection);
    }

    public GetUserGroupsRequest(Integer count) {
        this.setApiMethod(ApiMethod.GET_USER_GROUPS)
                .addParam(ApiRequestConstants.COUNT_PARAM, count);
    }

    public GetUserGroupsRequest(String anchor, PagingDirection pagingDirection, Integer count) {
        this.setApiMethod(ApiMethod.GET_USER_GROUPS);

        if (anchor != null) {
            addParam(ApiRequestConstants.ANCHOR_PARAM, anchor);
        }

        if (pagingDirection != null) {
            addParam(ApiRequestConstants.DIRECTION_PARAM, pagingDirection);
        }

        if (count != null) {
            addParam(ApiRequestConstants.COUNT_PARAM, count);
        }
    }
}
