package ru.odnoklassniki.responses.group;

import ru.odnoklassniki.common.group.Group;
import ru.odnoklassniki.responses.ApiResponse;

import java.util.Arrays;

public class GetUserGroupsResponse extends ApiResponse {

    private String anchor;
    private Group[] groups;

    public String getAnchor() {
        return anchor;
    }

    public Group[] getGroups() {
        return groups;
    }

    @Override
    public String toString() {
        return "GetUserGroupsResponse{" +
                "anchor='" + anchor + '\'' +
                ", groups=" + Arrays.toString(groups) +
                '}';
    }
}
