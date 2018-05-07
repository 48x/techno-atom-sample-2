package ru.odnoklassniki.tests;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;
import ru.odnoklassniki.base.ApiTestBase;
import ru.odnoklassniki.common.ApiException;
import ru.odnoklassniki.common.group.GroupBeanFields;
import ru.odnoklassniki.responses.group.GetInfoResponse;

import java.util.logging.Logger;

public class TestGetInfo extends ApiTestBase {
    private static final Logger LOGGER = Logger.getLogger(TestGetInfo.class.getSimpleName());
    private static final String ERROR_NO_SESSION = "PARAM : Missed required parameter: access_token";
    private static final String ERROR_NO_FIELDS_PARAM = "PARAM : Missing required parameter fields";
    private static final String ERROR_NO_UIDS_PARAM = "PARAM : Missing required parameter uids";

    /**
     * Проверка, что метод сессионный и требует указания access_token
     */
    @Test
    public void testMethodThrowsExceptionWhenSessionIsNotSet() {
        LOGGER.info("Проверим, что метод сессионный и требует указания access_token");
        try {
            LOGGER.info("Вызовем метод без сессии");
            okApi.getGroupService().getInfo();
            Assert.fail("Метод не кинул ошибку при отсутствии пользовательской сессии");
        } catch (ApiException e) {
            LOGGER.info("Получили ошибку, проверим её текст");
            Assert.assertEquals("Неверная ошибка при вызове без сессии", ERROR_NO_SESSION, e.getApiErrorInfo().getErrorMessage());
        }
        LOGGER.info("Метод сессионный и кидает верную ошибку при вызове без сессии");
    }

    @Test
    public void testMethodThrowsExceptionInvokingWithoutNecessaryParams() {
        LOGGER.info("Проверим, что метод выкинет ошибку, если не передать нужные параметры");

        try {
         LOGGER.info("Вызовем метод без параметров в сессии пользователя");

         bindDefaultUserSession();
         okApi.getGroupService().getInfo();
         Assert.fail("Метод не выкинул исключение при отсутствии нужных параметров");
        } catch (ApiException e) {
            LOGGER.info("Получили ошибку, проверим её текст");
            Assert.assertEquals("Неверная ошибка при вызове без нужных параметров", ERROR_NO_UIDS_PARAM, e.getApiErrorInfo().getErrorMessage());
        }
        LOGGER.info("Метод кидает верную ошибку при вызове без необходимых параметров");
    }

    @Test
    public void testMethodThrowsExceptionInvokingWithoutFieldsParam() {
        LOGGER.info("Проверим, что метод выкинет ошибку, если не передать fields");

        try {
            LOGGER.info("Вызовем метод без fields в сессии пользователя");
            bindDefaultUserSession();
            okApi.getGroupService().getInfo(new String[]{"53357785645192"});
            Assert.fail("Метод не выкинул ошибку при отсутсвии fields");
        } catch (ApiException e) {
            LOGGER.info("Получили ошибку, проверим её текст");
            Assert.assertEquals("Неверная ошибка при вызове без fields", ERROR_NO_FIELDS_PARAM, e.getApiErrorInfo().getErrorMessage());
        }

        LOGGER.info("Метод кидает верную ошибку при вызове без fields");
    }

    @Test
    public void testMethodThrowsExceptionInvokingWithoutUidsParam() {
        LOGGER.info("Проверим, что метод выкинет ошибку, если не передать uids");

        try {
            LOGGER.info("Вызовем метод без uids в сесссии пользователя");
            bindDefaultUserSession();
            okApi.getGroupService().getInfo(new GroupBeanFields[]{GroupBeanFields.NAME});
        } catch (ApiException e) {
            LOGGER.info("Получили ошибку, проверим её текст");
            Assert.assertEquals("Неверная ошибка при вызове без uids", ERROR_NO_UIDS_PARAM, e.getApiErrorInfo().getErrorMessage());
        }

        LOGGER.info("Метод кидает верную ошибку при вызове без uids");
    }

    @Test
    public void testFieldsParam() throws ApiException {
        LOGGER.info("Проверим, что метод возвращает только переданные параметры");

        LOGGER.info("Вызовем метод в сессии пользователя");
        bindDefaultUserSession();
        GetInfoResponse getInfoResponse = okApi.getGroupService().getInfo(new String[]{"53357785645192", "53038939046008"}, new GroupBeanFields[]{GroupBeanFields.NAME, GroupBeanFields.CITY});

        LOGGER.info("Проверим, что в ответе есть непустые имя и город группы");
        Assert.assertTrue("Пустое название группы", StringUtils.isNotBlank(getInfoResponse.getName()));
        Assert.assertTrue("Пустое название города", StringUtils.isNotBlank(getInfoResponse.getCity()));

        LOGGER.info("Проверим, что в ответе нет незапрашиваемого поля");
        Assert.assertNull("Пустое описание", getInfoResponse.getDescription());

        LOGGER.info("Метод по возвращает название группы и города по необходимым переданным параметрам");
    }

}
