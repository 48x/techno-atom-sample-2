package ru.odnoklassniki.services;

import ru.odnoklassniki.ApiClient;
import ru.odnoklassniki.common.ApiException;
import ru.odnoklassniki.common.group.GroupBean;
import ru.odnoklassniki.common.group.GroupBeanField;
import ru.odnoklassniki.requests.group.GroupGetInfoRequest;
import ru.odnoklassniki.requests.group.GroupGetUserGroupsV2Request;
import ru.odnoklassniki.responses.group.GroupGetUserGroupsV2Response;

/**
 * Created by Maksim Egorichev on 5/7/18 at 11:23 AM
 */
public class GroupService {
    private ApiClient apiClient;

    GroupService(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public GroupBean[] getGroupInfo(String[] uids, GroupBeanField[] groupBeanFields) throws ApiException {
        GroupGetInfoRequest groupGetInfoRequest = new GroupGetInfoRequest(uids, groupBeanFields);
        return apiClient.executeRequest(groupGetInfoRequest, GroupBean[].class);
    }

    public GroupBean[] getGroupInfo(String[] uids, GroupBeanField[] groupBeanFields, Boolean moveToTop) throws ApiException {
        GroupGetInfoRequest groupGetInfoRequest = new GroupGetInfoRequest(uids, groupBeanFields, moveToTop);
        return apiClient.executeRequest(groupGetInfoRequest, GroupBean[].class);
    }

    public GroupGetUserGroupsV2Response getUserGroupsV2() throws ApiException {
        GroupGetUserGroupsV2Request groupGetUserGroupsV2Request = new GroupGetUserGroupsV2Request();
        return apiClient.executeRequest(groupGetUserGroupsV2Request, GroupGetUserGroupsV2Response.class);
    }

    public GroupGetUserGroupsV2Response getUserGroupsV2(Integer count) throws ApiException {
        GroupGetUserGroupsV2Request groupGetUserGroupsV2Request = new GroupGetUserGroupsV2Request(count);
        return apiClient.executeRequest(groupGetUserGroupsV2Request, GroupGetUserGroupsV2Response.class);
    }
}
