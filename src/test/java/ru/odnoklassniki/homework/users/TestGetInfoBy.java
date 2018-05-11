package ru.odnoklassniki.homework.users;

import org.junit.Assert;
import org.junit.Test;
import ru.odnoklassniki.base.ApiTestBase;
import ru.odnoklassniki.common.ApiException;
import ru.odnoklassniki.common.group.UserBean;
import ru.odnoklassniki.common.users.UserInfoField;
import ru.odnoklassniki.responses.users.UsersGetInfoByResponse;

import java.util.logging.Logger;


/**
 * Created by Maxim Vyshegorodtsev
 *
 * Чек-лист проверок api метода users.getInfoBy
 *
 * 1. Проверка на то, что сессия обязательна
 *
 * 2. Проверка на то, что параметр uid обязателен
 *
 * 3. Проверка на то, что параметр fields обязателен
 *
 * 4. Проверка на то, что метод корректно отработает при введеных uids и fields (по дефолту)
 *
 * 5. Проверка на то, что метод выбросит ошибку, если ввести невалидный uid
 *
 */
public class TestGetInfoBy extends ApiTestBase {

    private static final Logger LOGGER = Logger.getLogger(TestGetInfoBy.class.getSimpleName());
    private static final String ERROR_NO_SESSION = "PARAM : Missed required parameter: access_token";
    private static final String ERROR_NO_UID = "PARAM : Missing required parameter uid";
    private static final String ERROR_NO_FIELDS = "PARAM : Missing required parameter fields";
    private static final String ERROR_INVALID_UID = "PARAM_USER_ID : Invalid uid";
    private static final String USER_ID_FOR_TEST = "572071876429";
    private static final String USER_NAME_FOR_TEST = "Юрий Дудь";
    private static final UserInfoField[] FIELDS = UserInfoField.values();

    /**
     * Проверка на то, что сессия обязательна для внешних приложений
     */

    @Test
    public void testMethodThrowsExceptionWhenSessionIsNotSet() {
        LOGGER.info("Проверим, что метод сессионный и требует указания access_token");

        try {
            LOGGER.info("Вызовем метод без сессии");
            okApi.getUserService().getInfoBy(USER_ID_FOR_TEST, new UserInfoField[]{});
            Assert.fail("Метод не кинул ошибку при отсутствии пользовательской сессии");
        } catch (ApiException e) {
            LOGGER.info("Получили ошибку, проверим её текст");
            Assert.assertEquals("Неверная ошибка при вызове без сессии", ERROR_NO_SESSION, e.getApiErrorInfo().getErrorMessage());
        }
        LOGGER.info("Метод сессионный и кидает верную ошибку при вызове без сессии");
    }

    /**
     * Проверка на то, что параметр uid обязателен
     */

    @Test
    public void testParamUidIsRequired() {
        LOGGER.info("Проверим, что метод выбросит исключение, если не указать uid");

        try {
            LOGGER.info("Вызовем метод в сессии пользователя");
            bindDefaultUserSession();
            okApi.getUserService().getInfoBy(null, new UserInfoField[]{});
            Assert.fail("Метод не кинул исключение при остутствии uid");
        } catch (ApiException e) {
            LOGGER.info("Получили ошибку, проверим её сообщение");
            Assert.assertEquals("Неверная ошибка при вызове без uid", ERROR_NO_UID, e.getApiErrorInfo().getErrorMessage());
        }

        LOGGER.info("Параметр uid обязателен");
    }

    /**
     * Проверка на то, что параметр fields обязателен
     */

    @Test
    public void testParamFieldsIsRequired() {
        LOGGER.info("Проверим, что метод выкинет ошибку, если не указать fields");

        try {
            LOGGER.info("Вызовем метод в сессии пользователя");
            bindDefaultUserSession();
            okApi.getUserService().getInfoBy(USER_ID_FOR_TEST, new UserInfoField[]{});
            Assert.fail("Метод не кинул ошибку при отсутствии fields");
        } catch (ApiException e) {
            LOGGER.info("Получили ошибку, проверим её сообщение");
            Assert.assertEquals("Неверная ошибка при вызове без fields", ERROR_NO_FIELDS, e.getApiErrorInfo().getErrorMessage());
        }

        LOGGER.info("Параметр fields обязателен");
    }

    /**
     * Проверка на то, что метод корректно отработает при введеных uids и fields (по дефолту)
     */

    @Test
    public void testMethodWithValidUidAndFields() throws ApiException {
        LOGGER.info("Проверим, что метод корректно отработает с параметрами uids, fields");

        LOGGER.info("Вызовем метод в сессии пользователя");
        bindDefaultUserSession();

        UsersGetInfoByResponse usersGetInfoByResponse = okApi.getUserService().getInfoBy(USER_ID_FOR_TEST, FIELDS);

        LOGGER.info("Проверим, что в ответе нужные данные о пользователе");
        Assert.assertNotNull("Метод выдал null", usersGetInfoByResponse);
        Assert.assertNotNull("В ответе вместо пользователя метод выдал null", usersGetInfoByResponse.getUser());

        LOGGER.info("Проверим, что полученные данные о пользователе верны");
        UserBean userBean = usersGetInfoByResponse.getUser();

        Assert.assertNotNull("В userBean uid == null", userBean.getUid());
        Assert.assertEquals("Неверный uid", userBean.getUid(), USER_ID_FOR_TEST);

        Assert.assertNotNull("В userBean name == null", userBean.getName());
        Assert.assertEquals("Неверный name", userBean.getName(), USER_NAME_FOR_TEST);

        LOGGER.info("Метод корректно отработал при введенных uids, fields");
    }

    /**
     * Проверка на то, что метод выбросит ошибку, если ввести невалидный uid
     */

    @Test
    public void testMethodOnInvalidUid() {

        LOGGER.info("Проверим, что метод выкинет ошибку, если ввести невалидный uid");

        try {
            LOGGER.info("Вызовем метод в сессии пользователя");
            bindDefaultUserSession();
            okApi.getUserService().getInfoBy("hfsfks", FIELDS);
            Assert.fail("Метод не кинул ошибку при невалидном uid");
        } catch (ApiException e) {
            LOGGER.info("Получили ошибку, проверим её сообщение");
            Assert.assertTrue("Неверная ошибка при вызове метода с невалидным uid",
                    e.getApiErrorInfo().getErrorMessage().startsWith(ERROR_INVALID_UID));
        }

        LOGGER.info("Метод кидает ошибку при вводе невалидного uid");
    }
}
