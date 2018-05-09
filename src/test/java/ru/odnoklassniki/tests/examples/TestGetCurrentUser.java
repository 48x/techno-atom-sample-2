package ru.odnoklassniki.tests.examples;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;
import ru.odnoklassniki.common.ApiException;
import ru.odnoklassniki.base.ApiTestBase;
import ru.odnoklassniki.common.users.UserInfoField;
import ru.odnoklassniki.responses.users.UsersGetCurrentUserResponse;

import java.util.logging.Logger;

/**
 * Created by Maksim Egorichev on 4/24/18 at 6:45 PM
 *
 * Тесты на метод users.getCurrentUser
 */
public class TestGetCurrentUser extends ApiTestBase {
    private static final Logger LOGGER = Logger.getLogger(TestGetCurrentUser.class.getSimpleName());
    private static final String ERROR_NO_SESSION = "PARAM : Missed required parameter: access_token";

    /**
     * Проверка, что метод сессионный и требует указания access_token
     */
    @Test
    public void testMethodThrowsExceptionWhenSessionIsNotSet() {
        LOGGER.info("Проверим, что метод сессионный и требует указания access_token");
        try {
            LOGGER.info("Вызовем метод без сессии");
            okApi.getUserService().getCurrentUser();
            Assert.fail("Метод не кинул ошибку при отсутствии пользовательской сессии");
        } catch (ApiException e) {
            LOGGER.info("Получили ошибку, проверим её текст");
            Assert.assertEquals("Неверная ошибка при вызове без сессии", ERROR_NO_SESSION, e.getApiErrorInfo().getErrorMessage());
        }
        LOGGER.info("Метод сессионный и кидает верную ошибку при вызове без сессии");
    }

    /**
     * Проверка, что по дефолту метод возвращает имя и фамилию пользователя
     */
    @Test
    public void testLastNameAndFirstNameAreReturnedByDefault() throws ApiException {
        LOGGER.info("Проверим, что по дефолту метод возвращает имя и фамилию пользователя");

        LOGGER.info("Вызовем метод в сессии пользователя");
        bindDefaultUserSession();
        UsersGetCurrentUserResponse getCurrentUserResponse = okApi.getUserService().getCurrentUser();

        LOGGER.info("Проверим, что в ответе есть непустые имя и фамилия");
        Assert.assertTrue("Пустое имя пользователя", StringUtils.isNotBlank(getCurrentUserResponse.getFirstName()));
        Assert.assertTrue("Пустая фамилия пользователя", StringUtils.isNotBlank(getCurrentUserResponse.getLastName()));
        LOGGER.info("Метод по дефолту возвращает имя и фамилию");
    }

    /**
     * Проверка, что учитывается параметр fields и в ответе возвращаются только запрошенные поля
     */
    @Test
    public void testFieldsParam() throws ApiException {
        LOGGER.info("Проверим, что учитывается параметр fields и в ответе возвращаются только запрошенные поля");

        LOGGER.info("Вызовем метод в сессии пользователя");
        bindDefaultUserSession();
        UsersGetCurrentUserResponse getCurrentUserResponse = okApi.getUserService().getCurrentUser(new UserInfoField[]{UserInfoField.FIRST_NAME});

        LOGGER.info("Проверим, что в ответе есть запрошенное поле");
        Assert.assertTrue("Пустое имя пользователя", StringUtils.isNotBlank(getCurrentUserResponse.getFirstName()));

        LOGGER.info("Проверим, что в ответе нет незапрошенного поля");
        Assert.assertNull("Пустая фамилия пользователя", getCurrentUserResponse.getLastName());
        LOGGER.info("Учитывается параметр fields и в ответе возвращаются только запрошенные поля");
    }
}
