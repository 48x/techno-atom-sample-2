package ru.odnoklassniki.base;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

/**
 * Created by Maksim Egorichev on 4/23/18
 *
 * Настройки API-приложения
 */
@PropertySource("classpath:/project.properties")
@Configuration
public class ApplicationConfig {

    @Value("${api.server.url}")
    private String apiServer;

    @Value("${api.app.public.key}")
    private String publicKey;

    @Value("${api.app.secret.key}")
    private String secretKey;

    @Value("${http.client.timeout}")
    private int httpClientTimeout;

    public String getApiServer() {
        return apiServer;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public int getHttpClientTimeout() {
        return httpClientTimeout;
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer placeHolderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Override
    public String toString() {
        return "ApplicationConfig{" +
                "apiServer='" + apiServer + '\'' +
                ", publicKey='" + publicKey + '\'' +
                ", secretKey='" + secretKey + '\'' +
                ", httpClientTimeout=" + httpClientTimeout +
                '}';
    }
}
