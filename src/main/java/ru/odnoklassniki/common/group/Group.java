package ru.odnoklassniki.common.group;

import ru.odnoklassniki.responses.ApiResponse;

public class Group extends ApiResponse {

    private String userId;
    private String groupId;
    private String status;

    public String getUserId() {
        return userId;
    }

    public String getGroupId() {
        return groupId;
    }

    public String getStatus() {
        return status;
    }
}
