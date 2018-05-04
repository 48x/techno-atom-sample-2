package ru.odnoklassniki.base;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import ru.odnoklassniki.ApiClient;
import ru.odnoklassniki.services.OkAPI;

/**
 * Created by Maksim Egorichev on 4/23/18
 *
 * Провайдер клиентов к API
 */
@Component
@ComponentScan(basePackageClasses = {ApplicationConfig.class})
public class OkApiProvider implements FactoryBean<OkAPI> {

    @Autowired
    private ApplicationConfig applicationConfig;

    @Override
    public OkAPI getObject() {
        return OkAPI.create(new ApiClient(
                applicationConfig.getSecretKey(),
                applicationConfig.getPublicKey(),
                applicationConfig.getApiServer(),
                applicationConfig.getHttpClientTimeout()
            )
        );
    }

    @Override
    public Class<?> getObjectType() {
        return OkAPI.class;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }
}
