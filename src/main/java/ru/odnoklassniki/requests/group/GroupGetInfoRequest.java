package ru.odnoklassniki.requests.group;

import ru.odnoklassniki.common.ApiMethod;
import ru.odnoklassniki.common.group.GroupBeanField;
import ru.odnoklassniki.requests.ApiRequest;
import ru.odnoklassniki.requests.ApiRequestConstants;

/**
 * Created by Maksim Egorichev on 5/7/18 at 11:15 AM
 */
public class GroupGetInfoRequest extends ApiRequest {

    public GroupGetInfoRequest(String[] uids, GroupBeanField[] groupBeanFields) {
        this.setApiMethod(ApiMethod.GROUP_GET_INFO)
                .addParam(ApiRequestConstants.UIDS_PARAM, uids)
                .addParam(ApiRequestConstants.FIELDS_PARAM, groupBeanFields);
    }

    public GroupGetInfoRequest(String[] uids, GroupBeanField[] groupBeanFields, Boolean moveToTop) {
        this.setApiMethod(ApiMethod.GROUP_GET_INFO)
                .addParam(ApiRequestConstants.UIDS_PARAM, uids)
                .addParam(ApiRequestConstants.FIELDS_PARAM, groupBeanFields)
                .addParam(ApiRequestConstants.MOVE_TO_TOP_PARAM, moveToTop);
    }
}
