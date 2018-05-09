package ru.odnoklassniki.services;

import ru.odnoklassniki.ApiClient;

/**
 * Created by Maksim Egorichev on 4/23/18 at 9:19 AM
 *
 * Клиент к API с сервисами
 */
public class OkAPI {
    private ApiClient apiClient;

    public ApiClient getApiClient() {
        return apiClient;
    }

    private OkAPI(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public static OkAPI create(ApiClient apiClient) {
        return new OkAPI(apiClient);
    }

    public UserService getUserService() {
        return new UserService(this.apiClient);
    }

    public GroupService getGroupService() {
        return new GroupService(this.apiClient);
    }
}
