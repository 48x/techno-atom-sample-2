package ru.odnoklassniki.responses.users;

import com.google.gson.annotations.SerializedName;
import ru.odnoklassniki.responses.ApiResponse;

/**
 * Created by Maksim Egorichev on 4/23/18 at 9:05 AM
 *
 * Результат выполнения метода users.getCurrentUser
 */
public class UsersGetCurrentUserResponse extends ApiResponse {
    private Integer age;
    private String birthday;
    private String email;
    @SerializedName("first_name")
    private String firstName;
    @SerializedName("last_name")
    private String lastName;

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
    public String toString() {
        return "GetCurrentUserResponse{" +
                "age=" + age +
                ", birthday='" + birthday + '\'' +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
