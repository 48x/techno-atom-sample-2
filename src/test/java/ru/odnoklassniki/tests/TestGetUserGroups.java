package ru.odnoklassniki.tests;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;
import ru.odnoklassniki.base.ApiTestBase;
import ru.odnoklassniki.common.ApiException;
import ru.odnoklassniki.common.group.PagingDirection;
import ru.odnoklassniki.responses.group.GetUserGroupsResponse;
import ru.odnoklassniki.responses.users.GetCurrentUserResponse;

import java.util.Arrays;
import java.util.logging.Logger;

public class TestGetUserGroups extends ApiTestBase {
    private static final Logger LOGGER = Logger.getLogger(TestGetUserGroups.class.getSimpleName());
    private static final String ERROR_NO_SESSION = "PARAM : Missed required parameter: access_token";

    @Test
    public void testMethodThrowsExceptionWithoutSessionIsNotSet() {
        LOGGER.info("Проверим, что метод сессионный и требует указания access_token");
        try {
            LOGGER.info("Вызовем метод без сессии");
            okApi.getGroupService().getUserGroups();
            Assert.fail("Метод не кинул ошибку при отсутствии пользовательской сессии");
        } catch (ApiException e) {
            LOGGER.info("Получили ошибку, проверим её текст");
            Assert.assertEquals("Неверная ошибка при вызове без сессии", ERROR_NO_SESSION, e.getApiErrorInfo().getErrorMessage());
        }
        LOGGER.info("Метод сессионный и кидает верную ошибку при вызове без сессии");
    }

    @Test
    public void testAnchorAndGroupsAreReturnedByDefault() throws ApiException {
        LOGGER.info("Проверим, что по дефолту метод возвращает anchor и группы пользователя");

        LOGGER.info("Вызовем метод в сессии пользователя");
        bindDefaultUserSession();
        GetUserGroupsResponse getUserGroupsResponse = okApi.getGroupService().getUserGroups();

        LOGGER.info("Проверим, что в ответе есть непустые anchor и группы");
        Assert.assertTrue("Пустой anchor", StringUtils.isNotBlank(getUserGroupsResponse.getAnchor()));
        Assert.assertTrue("Пустой groups", StringUtils.isNotBlank(Arrays.toString(getUserGroupsResponse.getGroups())));
        LOGGER.info("Метод по дефолту возвращает anchor и список групп");
    }

    @Test
    public void testAnchorAndAroundDirectionAndCountParams() throws ApiException {
        LOGGER.info("Проверим, что при передаче anchor, around direction, count метод возврашает переданный anchor и список групп в задднанном количестве");

        int count = 2;
        LOGGER.info("Вызовем метод в сессии пользователя");
        bindDefaultUserSession();
        GetUserGroupsResponse getUserGroupsResponse = okApi.getGroupService().getUserGroups("LTE4OTI2Mzc0NzQ6LTE4OTI2Mzc0ODQ=",
                PagingDirection.AROUND, count);

        LOGGER.info("Проверим, что в ответе есть непустые anchor и groups");
        Assert.assertTrue("Пустой anchor", StringUtils.isNotBlank(getUserGroupsResponse.getAnchor()));
        Assert.assertTrue("Пустой groups", StringUtils.isNotBlank(Arrays.toString(getUserGroupsResponse.getGroups())));

        LOGGER.info("Проверим, что в ответе количество групп равно переданному параметру count");
        Assert.assertNotNull("Количество групп не совпадает с count", getUserGroupsResponse.getGroups());

        LOGGER.info("Учитываются параметры count, anchor в ответе");
    }
}
