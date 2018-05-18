package ru.odnoklassniki.tests.examples;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.util.CollectionUtils;
import ru.odnoklassniki.base.ApiTestBase;
import ru.odnoklassniki.common.ApiException;
import ru.odnoklassniki.common.group.GroupBean;
import ru.odnoklassniki.common.group.UserGroupBean;
import ru.odnoklassniki.responses.group.GroupGetUserGroupsV2Response;

import java.util.List;
import java.util.logging.Logger;

/**
 * Created by Maksim Egorichev on 5/9/18 at 4:39 PM
 *
 * Updated by Dmitriy Tcibisov on 5/18/18 at 15:00 PM
 *
 * Тесты на метод group.getUserGroupsV2
 *
 * Чек-лист:
 * 1. Проверка, что метод сессионный и требует указания access_token
 * 2. Проверка, что метод работает без параметров (по-умолчанию)
 * 3. Проверка, что метод работает при некорректном параметре count, возвращая количество групп по-умолчанию
 * 4. Проверка, что метод работает при корректном параметре count, возвращая не больше групп чем указано
 * ToDo: 5. Проверка, что метод получает null вместо групп при отсутствии групп у пользователя
 */
public class TestGroupGetUserGroupsV2 extends ApiTestBase {
    private static final Logger LOGGER = Logger.getLogger(TestGroupGetUserGroupsV2.class.getSimpleName());
    private static final Integer COUNT_CORRECT = 2;
    private static final Integer COUNT_INCORRECT = 0;
    private static final String ERROR_NO_SESSION = "PARAM : Missed required parameter: access_token";

    /**
     * 1. Проверка, что метод сессионный и требует указания access_token
     */
    @Test
    public void testMethodThrowsExceptionWhenSessionIsNotSet() {
        LOGGER.info("Проверим, что метод сессионный и требует указания access_token");
        try {
            LOGGER.info("Вызовем метод без сессии");
            okApi.getGroupService().getUserGroupsV2();
            Assert.fail("Метод не кинул ошибку при отсутствии пользовательской сессии");
        } catch (ApiException e) {
            LOGGER.info("Получили ошибку, проверим её текст");
            Assert.assertEquals("Неверная ошибка при вызове без сессии", ERROR_NO_SESSION, e.getApiErrorInfo().getErrorMessage());
        }
        LOGGER.info("Метод сессионный и кидает верную ошибку при вызове без сессии");
    }

    /**
     * 2. Проверка, что метод работает без параметров (по-умолчанию)
     */
    @Test
    public void testDefaultParams() throws ApiException {
        LOGGER.info("Проверим, что метод работает без параметров (по-умолчанию) (пользователь с минимум одной группой)");
        LOGGER.info("Вызовем метод в сессии пользователя");
        bindDefaultUserSession();

        GroupGetUserGroupsV2Response groupGetUserGroupsV2Response = okApi.getGroupService().getUserGroupsV2();

        LOGGER.info("Проверим, что получили ответ");
        Assert.assertNotNull("Получили null в ответ на запрос", groupGetUserGroupsV2Response);

        LOGGER.info("Проверим, что в ответе есть группы");
        List<UserGroupBean> userGroupBean = groupGetUserGroupsV2Response.getGroups();
        Assert.assertNotNull("Получили null вместо списка групп", userGroupBean);
        Assert.assertFalse("Пустой список групп", CollectionUtils.isEmpty(userGroupBean));

        LOGGER.info("Проверим, содержание ответа");
        Assert.assertNotNull("Получили null вместо anchor", groupGetUserGroupsV2Response.getAnchor());
        Assert.assertFalse("Получили пустой anchor", groupGetUserGroupsV2Response.getAnchor().isEmpty());

        LOGGER.info("Метод работает верно при вызове без параметров (по-умолчанию)");
    }

    /**
     * 3. Проверка, что метод работает при некорректном параметре count, возвращая количество групп по-умолчанию
     */
    @Test
    public void testIfIncorrectCountParamWhenAllGroupsReturned() throws ApiException {
        LOGGER.info("Проверим, что метод работает при некорректном параметре count (пользователь с минимум одной группой)");
        LOGGER.info("Вызовем метод в сессии пользователя");
        bindDefaultUserSession();

        Integer count = COUNT_INCORRECT;
        GroupGetUserGroupsV2Response groupGetUserGroupsV2Response = okApi.getGroupService().getUserGroupsV2(count);

        LOGGER.info("Проверим, что получили ответ");
        Assert.assertNotNull("Получили null в ответ на запрос", groupGetUserGroupsV2Response);

        LOGGER.info("Проверим, что в ответе есть группы");
        List<UserGroupBean> userGroupBean = groupGetUserGroupsV2Response.getGroups();
        Assert.assertNotNull("Получили null вместо списка групп", userGroupBean);
        Assert.assertFalse("Пустой список групп", CollectionUtils.isEmpty(userGroupBean));

        LOGGER.info("Проверим, что количество групп превышает указанное в параметре count (некорректном)");
        Assert.assertTrue("Получили некорректное количество групп", userGroupBean.size() > count);

        LOGGER.info("Метод работает верно при некорректном параметре count");
    }

    /**
     * 4. Проверка, что метод работает при корректном параметре count, возвращая не больше групп чем указано
     */
    @Test
    public void testIfCorrectCountParamWhenCountOfGroupsReturned() throws ApiException {
        LOGGER.info("Проверим, что метод работает при корректном параметре count (пользователь с минимум одной группой)");
        LOGGER.info("Вызовем метод в сессии пользователя");
        bindDefaultUserSession();

        Integer count = COUNT_CORRECT;
        GroupGetUserGroupsV2Response groupGetUserGroupsV2Response = okApi.getGroupService().getUserGroupsV2(count);

        LOGGER.info("Проверим, что получили ответ");
        Assert.assertNotNull("Получили null в ответ на запрос", groupGetUserGroupsV2Response);

        LOGGER.info("Проверим, что в ответе есть группы");
        List<UserGroupBean> userGroupBean = groupGetUserGroupsV2Response.getGroups();
        Assert.assertNotNull("Получили null вместо списка групп", userGroupBean);
        Assert.assertFalse("Пустой список групп", CollectionUtils.isEmpty(userGroupBean));

        LOGGER.info("Проверим, что количество групп не превышает указанное в параметре count");
        Assert.assertTrue("Получили некорректное количество групп", userGroupBean.size() <= count);

        LOGGER.info("Метод работает верно при корректном параметре count");
    }
}
