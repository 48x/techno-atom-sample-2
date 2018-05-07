package ru.odnoklassniki.responses.group;

import com.google.gson.annotations.SerializedName;
import ru.odnoklassniki.responses.ApiResponse;

public class GetInfoResponse extends ApiResponse {

    private String city;
    private String country;
    private String description;
    private String name;
    @SerializedName("private")
    private Boolean hasPrivate;
    private Integer uid;

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public Boolean getHasPrivate() {
        return hasPrivate;
    }

    public Integer getUid() {
        return uid;
    }

    @Override
    public String toString() {
        return "GetInfoResponse{" +
                "city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", description='" + description + '\'' +
                ", name='" + name + '\'' +
                ", hasPrivate=" + hasPrivate +
                ", uid=" + uid +
                '}';
    }
}
