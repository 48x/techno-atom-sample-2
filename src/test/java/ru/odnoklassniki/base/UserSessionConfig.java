package ru.odnoklassniki.base;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Created by Maksim Egorichev on 4/23/18
 *
 * Параметры сессии пользователя
 */
@PropertySource("classpath:/project.properties")
@Configuration
public class UserSessionConfig {

    @Value("${user.session.access.token}")
    private String accessToken;

    @Value("${user.session.secret.key}")
    private String sessionSecretKey;

    public String getAccessToken() {
        return accessToken;
    }

    public String getSessionSecretKey() {
        return sessionSecretKey;
    }

    @Override
    public String toString() {
        return "UserSessionConfig{" +
                "accessToken='" + accessToken + '\'' +
                ", sessionSecretKey='" + sessionSecretKey + '\'' +
                '}';
    }
}