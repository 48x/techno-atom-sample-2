package ru.odnoklassniki.common.group;

import java.util.Objects;

/**
 * Created by Maksim Egorichev on 5/9/18 at 4:36 PM
 */
public class UserGroupBean {
    private String groupId;
    private GroupUserRole role;
    private String status;
    private String userId;

    public String getGroupId() {
        return groupId;
    }

    public GroupUserRole getRole() {
        return role;
    }

    public String getStatus() {
        return status;
    }

    public String getUserId() {
        return userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserGroupBean that = (UserGroupBean) o;
        return Objects.equals(groupId, that.groupId) &&
                role == that.role &&
                Objects.equals(status, that.status) &&
                Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(groupId, role, status, userId);
    }

    @Override
    public String toString() {
        return "UserGroupBean{" +
                "groupId='" + groupId + '\'' +
                ", role=" + role +
                ", status='" + status + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }
}
