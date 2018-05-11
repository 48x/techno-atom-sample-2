package ru.odnoklassniki.responses.group;

import ru.odnoklassniki.common.group.UserGroupBean;

import java.util.List;
import java.util.Objects;

/**
 * Created by Maksim Egorichev on 5/9/18 at 4:37 PM
 */
public class GroupGetUserGroupsV2Response {
    private String anchor;
    private List<UserGroupBean> groups;

    public String getAnchor() {
        return anchor;
    }

    public List<UserGroupBean> getGroups() {
        return groups;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GroupGetUserGroupsV2Response that = (GroupGetUserGroupsV2Response) o;
        return Objects.equals(anchor, that.anchor) &&
                Objects.equals(groups, that.groups);
    }

    @Override
    public int hashCode() {

        return Objects.hash(anchor, groups);
    }

    @Override
    public String toString() {
        return "GroupGetUserGroupsV2Response{" +
                "anchor='" + anchor + '\'' +
                ", groups=" + groups +
                '}';
    }
}
