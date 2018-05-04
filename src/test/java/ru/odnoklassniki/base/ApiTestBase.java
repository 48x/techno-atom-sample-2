package ru.odnoklassniki.base;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.odnoklassniki.services.OkAPI;

/**
 * Created by Maksim Egorichev on 4/23/18
 *
 * Базовый тестовый класс
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {OkApiProvider.class, UserSessionConfig.class})
public class ApiTestBase {

    @Autowired
    public OkAPI okApi;

    @Autowired
    private UserSessionConfig defaultUserSession;

    public void bindSession(String accessToken, String sessionKey) {
        okApi.getApiClient().bindSession(accessToken, sessionKey);
    }

    public void bindDefaultUserSession() {
        bindSession(defaultUserSession.getAccessToken(), defaultUserSession.getSessionSecretKey());
    }

    public void dropSession() {
        okApi.getApiClient().dropSession();
    }
}
