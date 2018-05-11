package ru.odnoklassniki.common.group;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

/**
 * Created by Maksim Egorichev on 5/9/18 at 4:51 PM
 */
public class UserBean {
    private String uid;
    private Integer age;
    private String birthday;
    private String email;
    @SerializedName("first_name")
    private String firstName;
    @SerializedName("last_name")
    private String lastName;
    private String name;

    public String getName() {
        return name;
    }

    public String getUid() {
        return uid;
    }

    public Integer getAge() {
        return age;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserBean userBean = (UserBean) o;
        return Objects.equals(uid, userBean.uid) &&
                Objects.equals(age, userBean.age) &&
                Objects.equals(birthday, userBean.birthday) &&
                Objects.equals(email, userBean.email) &&
                Objects.equals(firstName, userBean.firstName) &&
                Objects.equals(lastName, userBean.lastName) &&
                Objects.equals(name, userBean.name);
    }

    @Override
    public int hashCode() {

        return Objects.hash(uid, age, birthday, email, firstName, lastName, name);
    }

    @Override
    public String toString() {
        return "UserBean{" +
                "uid='" + uid + '\'' +
                ", age=" + age +
                ", birthday='" + birthday + '\'' +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
