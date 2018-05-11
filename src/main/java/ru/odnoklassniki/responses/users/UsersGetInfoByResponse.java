package ru.odnoklassniki.responses.users;

import ru.odnoklassniki.common.group.UserBean;
import ru.odnoklassniki.responses.ApiResponse;

import java.util.Objects;

/**
 * Created by Maksim Egorichev on 5/9/18 at 4:50 PM
 */
public class UsersGetInfoByResponse extends ApiResponse {
    UserBean user;

    public UserBean getUser() {
        return user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UsersGetInfoByResponse that = (UsersGetInfoByResponse) o;
        return Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {

        return Objects.hash(user);
    }

    @Override
    public String toString() {
        return "UsersGetInfoByResponse{" +
                "user=" + user +
                '}';
    }
}
