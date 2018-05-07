package ru.odnoklassniki.requests.group;

import ru.odnoklassniki.common.ApiMethod;
import ru.odnoklassniki.common.group.GroupBeanFields;
import ru.odnoklassniki.requests.ApiRequest;
import ru.odnoklassniki.requests.ApiRequestConstants;

public class GetInfoRequest extends ApiRequest {

    public GetInfoRequest(String[] uids, GroupBeanFields[] groupBeanFields) {
        this.setApiMethod(ApiMethod.GROUP_GET_INFO)
                .addParam(ApiRequestConstants.UIDS_PARAM, uids)
                .addParam(ApiRequestConstants.FIELDS_PARAM, groupBeanFields);
    }

    public GetInfoRequest(String[] uids, GroupBeanFields[] groupBeanFields, Boolean hasMoveToTop) {
        this.setApiMethod(ApiMethod.GROUP_GET_INFO)
                .addParam(ApiRequestConstants.UIDS_PARAM, uids)
                .addParam(ApiRequestConstants.FIELDS_PARAM, groupBeanFields)
                .addParam(ApiRequestConstants.MOVE_TO_TOP_PARAM, hasMoveToTop);
    }

    public GetInfoRequest() {
        this.setApiMethod(ApiMethod.GROUP_GET_INFO);
    }

    public GetInfoRequest(String[] uids) {
        this.setApiMethod(ApiMethod.GROUP_GET_INFO)
                .addParam(ApiRequestConstants.UIDS_PARAM, uids);
    }

    public GetInfoRequest(GroupBeanFields[] groupBeanFields) {
        this.setApiMethod(ApiMethod.GROUP_GET_INFO)
                .addParam(ApiRequestConstants.FIELDS_PARAM, groupBeanFields);
    }
}
