package ru.odnoklassniki.tests.examples;

import org.junit.Assert;
import org.junit.Test;
import ru.odnoklassniki.base.ApiTestBase;
import ru.odnoklassniki.common.ApiException;
import ru.odnoklassniki.common.group.UserBean;
import ru.odnoklassniki.common.users.UserInfoField;
import ru.odnoklassniki.responses.users.UsersGetInfoByResponse;

import java.util.logging.Logger;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by Maksim Egorichev on 5/9/18 at 4:54 PM
 */
public class TestUsersGetInfoBy extends ApiTestBase {
    private static final Logger LOGGER = Logger.getLogger(TestUsersGetInfoBy.class.getSimpleName());
    private static final String USER_ID = "572071876429";
    private static final String USER_NAME = "Юрий Дудь";
    private static final UserInfoField[] FIELDS = UserInfoField.values();
    private static final String INVALID_USER_ID = "BlaBlaBla";
    private static final String ERROR_ID_IS_INVALID = "PARAM : Invalid UID value BlaBlaBla";
    private static final String ERROR_NO_SESSION = "PARAM : Missed required parameter: access_token";
    private static final String ERROR_ID_IS_NOT_SET = "PARAM : Missing required parameter uids";
    private static final String ERROR_FIELDS_IS_NOT_SET = "PARAM : Missing required parameter fields";

    /**
     * Пример теста на метод users.getInfoBy
     */
    @Test
    public void testUsersGetInfoByExample() throws ApiException {
        LOGGER.info("Получим данные о другом пользователе");
        bindDefaultUserSession();

        UsersGetInfoByResponse getInfoByResponse = okApi.getUserService().getInfoBy(USER_ID, FIELDS);

        LOGGER.info("Проверим, что получили верные данные");
        UserBean userBean = getInfoByResponse.getUser();
        Assert.assertNotNull("Не получили информацию о пользователе", userBean);
        Assert.assertEquals("Неверный ID пользователя", USER_ID, userBean.getUid());
        Assert.assertEquals("Неверное имя пользователя", USER_NAME, userBean.getName());
    }

    @Test
    public void testResponseFields() throws ApiException {
        LOGGER.info("Проверим, что в ответе только нужные поля");
        bindDefaultUserSession();

        UsersGetInfoByResponse getInfoByResponse = okApi.getUserService().getInfoBy(USER_ID, FIELDS);

        LOGGER.info("Проверим, что получили верные данные");
        LOGGER.info("Проверим, что получили верные данные");
        UserBean userBean = getInfoByResponse.getUser();
        Assert.assertNotNull("Не получили информацию о пользователе", userBean);
        Assert.assertTrue("Пустой id", StringUtils.isNotBlank(userBean.getUid()));
        Assert.assertTrue("Пустое имя", StringUtils.isNotBlank(userBean.getName()));

        LOGGER.info("Проверим, что не получили ненужных полей");
        Assert.assertEquals("Пустой age", userBean.getAge().intValue(),0);
        Assert.assertNull("Непустой birthday",userBean.getBirthday());
        Assert.assertNull("Непустой email",userBean.getEmail());
        Assert.assertNull("Непустой firstName",userBean.getFirstName());
        Assert.assertNull("Непустой lastName",userBean.getLastName());
    }

    @Test
    public void testUsersGetInfoByThrowsExceptionWhenUidIsInvalid() throws ApiException {
        LOGGER.info("Проверим, что метод выбрасывает исключение при не валидном id");
        bindDefaultUserSession();

        try {
            LOGGER.info("Вызовем метод с невалидным id");
            okApi.getUserService().getInfoBy(INVALID_USER_ID,FIELDS);
            Assert.fail("Метод не кинул ошибку с невалидным id");
        } catch (ApiException e) {
            LOGGER.info("Получили ошибку, проверим её текст");
            Assert.assertEquals("Неверная ошибка при вызове без сессии", ERROR_ID_IS_INVALID, e.getApiErrorInfo().getErrorMessage());
        }
    }

    @Test
    public void testGetInfoByThrowsExceptionWhenSessionIsNotSet() throws ApiException {
        LOGGER.info("Проверим, что метод сессионный и требует указания access_token");

        try {
            LOGGER.info("Вызовем метод без сессии");
            okApi.getUserService().getInfoBy(USER_ID,FIELDS);
            Assert.fail("Метод не кинул ошибку при отсутствии пользовательской сессии");
        } catch (ApiException e) {
            LOGGER.info("Получили ошибку, проверим её текст");
            Assert.assertEquals("Неверная ошибка при вызове без сессии", ERROR_NO_SESSION, e.getApiErrorInfo().getErrorMessage());
        }
    }

    @Test
    public void testGetInfoByThrowsExceptionWhenUidIsNotSet() throws ApiException {
        LOGGER.info("Проверим, что метод выбрасывает исключение, если не установлен id");
        bindDefaultUserSession();

        try {
            LOGGER.info("Вызовем метод без id");
            okApi.getUserService().getInfoBy(null,FIELDS);
            Assert.fail("Метод не кинул ошибку без id");
        } catch (ApiException e) {
            LOGGER.info("Получили ошибку, проверим её текст");
            Assert.assertEquals("Неверная ошибка при вызове без id", ERROR_ID_IS_NOT_SET, e.getApiErrorInfo().getErrorMessage());
        }
    }

    @Test
    public void testGetInfoByThrowsExceptionWhenFieldsIsNotSet() throws ApiException {
        LOGGER.info("Проверим, что метод выбрасывает исключение, если не установлены fields");
        bindDefaultUserSession();

        try {
            LOGGER.info("Вызовем метод без fields");
            okApi.getUserService().getInfoBy(INVALID_USER_ID,null);
            Assert.fail("Метод не кинул ошибку без fields");
        } catch (ApiException e) {
            LOGGER.info("Получили ошибку, проверим её текст");
            Assert.assertEquals("Неверная ошибка при вызове без fields", ERROR_FIELDS_IS_NOT_SET, e.getApiErrorInfo().getErrorMessage());
        }
    }

}
