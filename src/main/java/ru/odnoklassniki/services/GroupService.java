package ru.odnoklassniki.services;

import ru.odnoklassniki.ApiClient;
import ru.odnoklassniki.common.ApiException;
import ru.odnoklassniki.common.group.GroupBeanFields;
import ru.odnoklassniki.common.group.PagingDirection;
import ru.odnoklassniki.requests.group.GetInfoRequest;
import ru.odnoklassniki.requests.group.GetUserGroupsRequest;
import ru.odnoklassniki.responses.group.GetInfoResponse;
import ru.odnoklassniki.responses.group.GetUserGroupsResponse;

public class GroupService {

    private ApiClient apiClient;

    public GroupService(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public GetInfoResponse getInfo(String[] uids, GroupBeanFields[] groupBeanFields) throws ApiException {
        GetInfoRequest getInfoRequest = new GetInfoRequest(uids, groupBeanFields);
        return apiClient.executeRequest(getInfoRequest, GetInfoResponse.class);
    }

    public GetInfoResponse getInfo(String[] uids, GroupBeanFields[] groupBeanFields, Boolean hasMoveToTop) throws ApiException {
        GetInfoRequest getInfoRequest = new GetInfoRequest(uids, groupBeanFields, hasMoveToTop);
        return apiClient.executeRequest(getInfoRequest, GetInfoResponse.class);
    }

    public GetInfoResponse getInfo() throws ApiException {
        GetInfoRequest getInfoRequest = new GetInfoRequest();
        return apiClient.executeRequest(getInfoRequest, GetInfoResponse.class);
    }

    public GetInfoResponse getInfo(String[] uids) throws ApiException {
        GetInfoRequest getInfoRequest = new GetInfoRequest(uids);
        return apiClient.executeRequest(getInfoRequest, GetInfoResponse.class);
    }

    public GetInfoResponse getInfo(GroupBeanFields[] groupBeanFields) throws ApiException {
        GetInfoRequest getInfoRequest = new GetInfoRequest(groupBeanFields);
        return apiClient.executeRequest(getInfoRequest, GetInfoResponse.class);
    }

    public GetUserGroupsResponse getUserGroups() throws ApiException {
        GetUserGroupsRequest getUserGroupsRequest = new GetUserGroupsRequest();
        return apiClient.executeRequest(getUserGroupsRequest, GetUserGroupsResponse.class);
    }

    public GetUserGroupsResponse getUserGroups(String anchor) throws ApiException {
        GetUserGroupsRequest getUserGroupsRequest = new GetUserGroupsRequest(anchor);
        return apiClient.executeRequest(getUserGroupsRequest, GetUserGroupsResponse.class);
    }

    public GetUserGroupsResponse getUserGroups(PagingDirection pagingDirection) throws ApiException {
        GetUserGroupsRequest getUserGroupsRequest = new GetUserGroupsRequest(pagingDirection);
        return apiClient.executeRequest(getUserGroupsRequest, GetUserGroupsResponse.class);
    }

    public GetUserGroupsResponse getUserGroups(Integer count) throws ApiException {
        GetUserGroupsRequest getUserGroupsRequest = new GetUserGroupsRequest(count);
        return apiClient.executeRequest(getUserGroupsRequest, GetUserGroupsResponse.class);
    }

    public GetUserGroupsResponse getUserGroups(String anchor, PagingDirection pagingDirection, Integer count) throws ApiException {
        GetUserGroupsRequest getUserGroupsRequest = new GetUserGroupsRequest(anchor, pagingDirection, count);
        return apiClient.executeRequest(getUserGroupsRequest, GetUserGroupsResponse.class);
    }
}
