package ru.odnoklassniki.common.group;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

/**
 * Created by Maksim Egorichev on 5/7/18 at 11:36 AM
 */
public class GroupBean {
    private String uid;
    private String name;
    private String description;
    private String shortname;
    private String picAvatar;
    @SerializedName("members_count")
    private int membersCount;
    private String status;

    public String getUid() {
        return uid;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getShortname() {
        return shortname;
    }

    public String getPicAvatar() {
        return picAvatar;
    }

    public int getMembersCount() {
        return membersCount;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GroupBean groupBean = (GroupBean) o;
        return membersCount == groupBean.membersCount &&
                Objects.equals(uid, groupBean.uid) &&
                Objects.equals(name, groupBean.name) &&
                Objects.equals(description, groupBean.description) &&
                Objects.equals(shortname, groupBean.shortname) &&
                Objects.equals(picAvatar, groupBean.picAvatar) &&
                Objects.equals(status, groupBean.status);
    }

    @Override
    public int hashCode() {

        return Objects.hash(uid, name, description, shortname, picAvatar, membersCount, status);
    }

    @Override
    public String toString() {
        return "GroupBean{" +
                "uid='" + uid + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", shortname='" + shortname + '\'' +
                ", picAvatar='" + picAvatar + '\'' +
                ", membersCount=" + membersCount +
                ", status='" + status + '\'' +
                '}';
    }
}
