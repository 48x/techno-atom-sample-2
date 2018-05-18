package ru.odnoklassniki.tests.examples;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;
import ru.odnoklassniki.base.ApiTestBase;
import ru.odnoklassniki.common.ApiException;
import ru.odnoklassniki.common.group.UserBean;
import ru.odnoklassniki.common.users.UserInfoField;
import ru.odnoklassniki.responses.users.UsersGetInfoByResponse;

import java.util.logging.Logger;

/**
 * Created by Maksim Egorichev on 5/9/18 at 4:54 PM
 *
 * Updated by Dmitriy Tcibisov on 5/17/18 at 9:45 PM
 *
 * Тесты на метод users.getInfoBy
 *
 * Чек-лист:
 * 1. Проверка, что метод сессионный и требует указания access_token
 * 2. Проверка, что метод выполняется без ошибок при корректных параметрах uid и fields
 * 3. Проверка, что метод выбрасывает исключение при некорректном параметре uid
 * 4. Проверка, что метод выбрасывает исключение при отсутствии обязательного параметра uid
 * 5. Проверка, что метод выбрасывает исключение при отсутствии обязательного параметра fields
 * 6. Проверка, что учитывается параметр fields и в ответе возвращаются только запрошенные поля
 */
public class TestUsersGetInfoBy extends ApiTestBase {
    private static final Logger LOGGER = Logger.getLogger(TestUsersGetInfoBy.class.getSimpleName());
    private static final String USER_INCORRECT_ID = "Incorrect";
    private static final String USER_ID = "572071876429";
    private static final String USER_NAME = "Юрий Дудь";
    private static final UserInfoField[] FIELDS = UserInfoField.values();
    private static final String ERROR_NO_SESSION = "PARAM : Missed required parameter: access_token";
    private static final String ERROR_INCORRECT_UID = "PARAM_USER_ID : Invalid uid";
    private static final String ERROR_NO_UID = "PARAM : Missing required parameter uid";
    private static final String ERROR_NO_FIELDS = "PARAM : Missing required parameter fields";

    /**
     * 1. Проверка, что метод сессионный и требует указания access_token
     */
    @Test
    public void testMethodThrowsExceptionWhenSessionIsNotSet() {
        LOGGER.info("Проверим, что метод сессионный и требует указания access_token");
        try {
            LOGGER.info("Вызовем метод без сессии");
            okApi.getUserService().getInfoBy(USER_ID, FIELDS);
            Assert.fail("Метод не кинул ошибку при отсутствии пользовательской сессии");
        } catch (ApiException e) {
            LOGGER.info("Получили ошибку, проверим её текст");
            Assert.assertEquals("Неверная ошибка при вызове без сессии", ERROR_NO_SESSION, e.getApiErrorInfo().getErrorMessage());
        }
        LOGGER.info("Метод сессионный и кидает верную ошибку при вызове без сессии");
    }

    /**
     * 2. Проверка, что метод выполняется без ошибок при корректных параметрах uid и fields
     */
    @Test
    public void testMethodNoThrowsExceptionWhenUidAndFieldsAreCorrect() throws ApiException {
        LOGGER.info("Проверим, что метод отдаёт верные данные при корректных uid и fields");
        LOGGER.info("Вызовем метод в сессии пользователя");
        bindDefaultUserSession();

        UsersGetInfoByResponse getInfoByResponse = okApi.getUserService().getInfoBy(USER_ID, FIELDS);
        LOGGER.info("Проверим, что данные были получены");
        Assert.assertNotNull("Получили null в ответ на запрос", getInfoByResponse);
        Assert.assertNotNull("Получили null вместо информации о пользователе", getInfoByResponse.getUser());

        LOGGER.info("Проверим, что получили верные данные");
        UserBean userBean = getInfoByResponse.getUser();
        Assert.assertEquals("Неверный ID пользователя", USER_ID, userBean.getUid());
        Assert.assertEquals("Неверное имя пользователя", USER_NAME, userBean.getName());

        LOGGER.info("Метод выполняется без ошибок и отдаёт корректные данные при введённых uid и fields");
    }

    /**
     * 3. Проверка, что метод выбрасывает исключение при некорректном параметре uid
     */
    @Test
    public void testMethodThrowsExceptionWhenUidIsIncorrect() {
        LOGGER.info("Проверим, что метод выбрасывает исключение при некорректном uid");
        try {
            LOGGER.info("Вызовем метод в сессии пользователя");
            bindDefaultUserSession();
            LOGGER.info("Вызовем метод с некорректным uid");
            okApi.getUserService().getInfoBy(USER_INCORRECT_ID , FIELDS);
            Assert.fail("Метод не кинул ошибку при некорректном uid");
        }
        catch (ApiException e) {
            LOGGER.info("Получили ошибку, проверим её текст");
            Assert.assertTrue("Неверная ошибка при вызове с некорректным uid",
                    e.getApiErrorInfo().getErrorMessage().startsWith(ERROR_INCORRECT_UID));
            /*Assert.assertEquals("Неверная ошибка при вызове с некорректным uid",
                    ERROR_INCORRECT_UID + " [" + USER_INCORRECT_ID + "]",
                    e.getApiErrorInfo().getErrorMessage());*/
        }
        LOGGER.info("Метод ожидает корректный uid и выбрасывает верную ошибку при вызове с некорректным uid");
    }

    /**
     * 4. Проверка, что метод выбрасывает исключение при отсутствии обязательного параметра uid
     */
    @Test
    public void testMethodThrowsExceptionWhenUidIsNotSend() {
        LOGGER.info("Проверим, что метод требует указания uid");
        try {
            LOGGER.info("Вызовем метод в сессии пользователя");
            bindDefaultUserSession();
            LOGGER.info("Вызовем метод без указания uid");
            okApi.getUserService().getInfoBy(null, FIELDS);
            Assert.fail("Метод не кинул ошибку при отсутствии uid");
        } catch (ApiException e) {
            LOGGER.info("Получили ошибку, проверим её текст");
            Assert.assertEquals("Неверная ошибка при вызове без указания uid", ERROR_NO_UID, e.getApiErrorInfo().getErrorMessage());
        }
        LOGGER.info("Метод ожидает uid и кидает верную ошибку при вызове без указания uid");
    }

    /**
     * 5. Проверка, что метод выбрасывает исключение при отсутствии обязательного параметра fields
     */
    @Test
    public void testMethodThrowsExceptionWhenFieldsIsNotSend() {
        LOGGER.info("Проверим, что метод требует указания fields");
        try {
            LOGGER.info("Вызовем метод в сессии пользователя");
            bindDefaultUserSession();
            LOGGER.info("Вызовем метод без указания fields");
            okApi.getUserService().getInfoBy(USER_ID, new UserInfoField[]{});
            Assert.fail("Метод не кинул ошибку при отсутствии fields");
        } catch (ApiException e) {
            LOGGER.info("Получили ошибку, проверим её текст");
            Assert.assertEquals("Неверная ошибка при вызове без указания fields", ERROR_NO_FIELDS, e.getApiErrorInfo().getErrorMessage());
        }
        LOGGER.info("Метод ожидает fields и кидает верную ошибку при вызове без указания fields");
    }

    /**
     * 6. Проверка, что учитывается параметр fields и в ответе возвращаются только запрошенные поля
     */
    @Test
    public void testFieldsParam() throws ApiException {
        LOGGER.info("Проверим, что учитывается параметр fields и в ответе возвращаются только запрошенные поля");

        LOGGER.info("Вызовем метод в сессии пользователя");
        bindDefaultUserSession();

        UsersGetInfoByResponse getInfoByResponse = okApi.getUserService().getInfoBy(USER_ID, new UserInfoField[]{UserInfoField.FIRST_NAME});
        UserBean userBean = getInfoByResponse.getUser();

        LOGGER.info("Проверим, что в ответе есть запрошенное поле");
        Assert.assertTrue("Пустое имя пользователя", StringUtils.isNotBlank(userBean.getFirstName()));
        LOGGER.info("Проверим, что в ответе нет незапрошенного поля");
        Assert.assertNull("Не пустая фамилия пользователя", userBean.getLastName());

        LOGGER.info("Учитывается параметр fields и в ответе возвращаются только запрошенные поля");
    }
}
