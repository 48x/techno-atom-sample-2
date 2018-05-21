package ru.odnoklassniki.tests.examples;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.util.CollectionUtils;
import ru.odnoklassniki.base.ApiTestBase;
import ru.odnoklassniki.common.ApiException;
import ru.odnoklassniki.responses.group.GroupGetUserGroupsV2Response;
import ru.odnoklassniki.common.group.UserGroupBean;

import java.util.logging.Logger;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by Maksim Egorichev on 5/9/18 at 4:39 PM
 */
public class  TestGroupGetUserGroupsV2 extends ApiTestBase {
    private static final Logger LOGGER = Logger.getLogger(TestGroupGetUserGroupsV2.class.getSimpleName());
    private static final Integer VALID_COUNT = 1;
    private static final Integer BIGGER_COUNT = 5;
    private static final Integer INVALID_COUNT = -5;
    private static final String ERROR_NO_SESSION = "PARAM : Missed required parameter: access_token";

    @Test
    public void testGroupGetUserGroupsV2ReturnCorrectDataWithDefaultParameters() throws ApiException {
        LOGGER.info("Получим данные о группах пользвоателя c дефолтными значениями");
        bindDefaultUserSession();

        GroupGetUserGroupsV2Response groupGetUserGroupsV2Response = okApi.getGroupService().getUserGroupsV2();

        LOGGER.info("Проверим, что не получили null");
        Assert.assertNotNull("Получили null в ответ на запрос", groupGetUserGroupsV2Response);

        LOGGER.info("Проверим, что в ответе есть группы");
        Assert.assertFalse("Пустой список групп", CollectionUtils.isEmpty(groupGetUserGroupsV2Response.getGroups()));

        LOGGER.info("Проверим, что в ответе есть anchor");
        Assert.assertFalse("Получили пустой anchor", StringUtils.isNotBlank(groupGetUserGroupsV2Response.getAnchor()));

    }

    @Test
    public void testGroupGetUserGroupsV2ReturnCorrectDataWithValidCount() throws ApiException {
        LOGGER.info("Получим данные о группах с валидным значением count");
        bindDefaultUserSession();

        GroupGetUserGroupsV2Response groupGetUserGroupsV2Response = okApi.getGroupService().getUserGroupsV2(VALID_COUNT);

        LOGGER.info("Проверим, что в ответе есть группы");
        Assert.assertFalse("Пустой список групп", CollectionUtils.isEmpty(groupGetUserGroupsV2Response.getGroups()));
        List<UserGroupBean> userGroupBean = groupGetUserGroupsV2Response.getGroups();

        LOGGER.info("Проверим, что в ответе правильное количество групп");
        Assert.assertTrue("Количество групп в ответе больше count", userGroupBean.size() <= VALID_COUNT.intValue());
    }

    @Test
    public void testGroupGetUserGroupsV2WorkCorrectlyWhenActualNumberOfGroupsLessThanCount() throws ApiException {
        LOGGER.info("Проверим, что метод работает правильно когда количество групп у пользователя мень, чем count");
        bindDefaultUserSession();

        GroupGetUserGroupsV2Response groupGetUserGroupsV2Response = okApi.getGroupService().getUserGroupsV2(BIGGER_COUNT);
        LOGGER.info("Проверим, что в ответе есть группы");
        Assert.assertFalse("Пустой список групп", CollectionUtils.isEmpty(groupGetUserGroupsV2Response.getGroups()));
        List<UserGroupBean> userGroupBean = groupGetUserGroupsV2Response.getGroups();

        LOGGER.info("Проверим, что в ответе правильное количество групп");
        Assert.assertTrue("Количество групп в ответе больше или равно count", userGroupBean.size() < BIGGER_COUNT.intValue());
    }

    @Test
    public void testGroupGetUserGroupsV2ThrowExceptionWhenSessionIsNotSet() throws ApiException {
        LOGGER.info("Проверим, что метод сессионный и требует установления acсess_token");

        try {
            okApi.getGroupService().getUserGroupsV2();
            Assert.fail("Метод не кинул ошибку при отсутствии пользовательской сессии");
        } catch (ApiException e) {
            LOGGER.info("Получили ошибку, проверим её текст");
            Assert.assertEquals("Неверная ошибка при вызове без сессии", ERROR_NO_SESSION, e.getApiErrorInfo().getErrorMessage());
        }
    }


    @Test
    public void testGroupGetUserGroupsV2ThrowExceptionWhenCountIsNegative() throws ApiException {
        LOGGER.info("Проверим, что метод возвращает дефолтное количесвто групп, если count меньше нуля");
        bindDefaultUserSession();

        GroupGetUserGroupsV2Response groupGetUserGroupsV2Response = okApi.getGroupService().getUserGroupsV2(INVALID_COUNT);
        LOGGER.info("Проверим, что в ответе есть группы");
        Assert.assertFalse("Пустой список групп", CollectionUtils.isEmpty(groupGetUserGroupsV2Response.getGroups()));
    }
}
