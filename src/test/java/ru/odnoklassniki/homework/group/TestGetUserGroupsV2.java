package ru.odnoklassniki.homework.group;

import org.junit.Assert;
import org.junit.Test;
import ru.odnoklassniki.base.ApiTestBase;
import ru.odnoklassniki.common.ApiException;
import ru.odnoklassniki.common.group.UserGroupBean;
import ru.odnoklassniki.responses.group.GroupGetUserGroupsV2Response;

import java.util.List;
import java.util.logging.Logger;

/**
 *     Created by Maxim Vyshegorodtsev
 *
 *      Чек-лист проверок api метода group.getUserGroupsV2
 *
 *      1. Проверка на то, что сессия обязательна для внешних приложений
 *
 *      2. Проверка на то, что метод работает корректно, если вызвать его без параметров(по дефолту)
 *
 *      3. Проверка на то, что метод вернет количество групп, совпадающее с count
 *
 *      4. Проверка на то, что метод при вызове в сессии не пропустит параметр uid
 *
 *      5. Проверка на то, что метод выдаст ответ по умолчанию, если ввести невалидный count
 */
public class TestGetUserGroupsV2 extends ApiTestBase {

    private static final Logger LOGGER = Logger.getLogger(TestGetUserGroupsV2.class.getSimpleName());
    private static final String ERROR_NO_SESSION = "PARAM : Missed required parameter: access_token";
    private static final String ERROR_UID_PARAM = "PARAM : Only one of session_key or uid must be specified";
    private static final String UID_PARAM_FOR_TEST = "449935655102";
    private static final String[] GROUP_IDS_FOR_TEST = new String[]{"53357785645192", "51722779689102", "53038939046008"};


    /**
     * Проверка на то, что сессия обязательна для внешних приложений
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
     * Проверка на то, что метод работает корректно, если вызвать его без параметров(по дефолту)
     */

    @Test
    public void testGroupsAreReturnedByDefault() throws ApiException {
        LOGGER.info("Проверим данные о группах, если вызвать метод по дефолту");

        LOGGER.info("Вызовем метод в сессии пользователя");
        bindDefaultUserSession();
        GroupGetUserGroupsV2Response groupGetUserGroupsV2Response = okApi.getGroupService().getUserGroupsV2();

        LOGGER.info("Проверим, что в ответе есть группы");
        Assert.assertNotNull("Метод вернул null", groupGetUserGroupsV2Response);
        Assert.assertNotNull("Метод вернул вместо groups null", groupGetUserGroupsV2Response.getGroups());
        Assert.assertFalse("Ответ пустой", groupGetUserGroupsV2Response.getGroups().isEmpty());
        Assert.assertNotNull("Метод вернул вместо anchor null", groupGetUserGroupsV2Response.getAnchor());
        Assert.assertFalse("Метод вернул пустой anchor", groupGetUserGroupsV2Response.getAnchor().isEmpty());

        LOGGER.info("Проверим, что количество групп правильное");
        List<UserGroupBean> userGroupBeans = groupGetUserGroupsV2Response.getGroups();
        Assert.assertEquals("Количество групп неверное", userGroupBeans.size(), 3);

        LOGGER.info("Проверим group_id каждой группы");

        for (int i = 0; i < userGroupBeans.size(); i++) {
            Assert.assertEquals("У группы не совпадает group_id", userGroupBeans.get(i).getGroupId(), GROUP_IDS_FOR_TEST[i]);
        }

        LOGGER.info("Проверим user_id каждой группы");
        for (UserGroupBean userGroupBean : userGroupBeans) {
            Assert.assertEquals("У группы не совпадает user_id", userGroupBean.getUserId(), UID_PARAM_FOR_TEST);
        }

        LOGGER.info("Метод рабоает корректно при вызове без параметров");
    }

    /**
     * Проверка на то, что метод вернет количество групп, совпадающее с count
     */

    @Test
    public void testCountParam() throws ApiException {
        LOGGER.info("Проверим, что количество групп в ответе совпадает с count");
        int count = 2;

        LOGGER.info("Вызовем метод в сессии пользователя");
        bindDefaultUserSession();
        GroupGetUserGroupsV2Response groupGetUserGroupsV2Response = okApi.getGroupService().getUserGroupsV2(count);

        LOGGER.info("Проверим, что в ответе есть группы");
        Assert.assertNotNull("Метод вернул null", groupGetUserGroupsV2Response);
        Assert.assertNotNull("Метод вернул вместо groups null", groupGetUserGroupsV2Response.getGroups());
        Assert.assertFalse("Ответ пустой", groupGetUserGroupsV2Response.getGroups().isEmpty());
        Assert.assertNotNull("Метод вернул вместо anchor null", groupGetUserGroupsV2Response.getAnchor());
        Assert.assertFalse("Метод вернул пустой anchor", groupGetUserGroupsV2Response.getAnchor().isEmpty());

        LOGGER.info("Проверим количество групп");
        Assert.assertEquals("Количество групп не совпадает с count", groupGetUserGroupsV2Response.getGroups().size(), count);

        LOGGER.info("Метод возвращает количество групп равное count");
    }

    /**
     * Проверка на то, что метод при вызове в сессии не пропустит параметр uid
     */

    @Test
    public void TestUidParam() {
        LOGGER.info("Проверим, что метод при вызвое его в сессии пользователя кидает ошибку, при передаче в метод параметра uid");
        try {
            LOGGER.info("Вызовем метод в сессии пользователя");
            bindDefaultUserSession();
            okApi.getGroupService().getUserGroupsV2(UID_PARAM_FOR_TEST);
            Assert.fail("Метод не выкинул ошибку");
        }
        catch (ApiException e) {
            LOGGER.info("Получили ошибку проверили её сообщение");
            Assert.assertEquals("Невеная ошибка", ERROR_UID_PARAM, e.getApiErrorInfo().getErrorMessage());
        }

        LOGGER.info("Метод кидает ошибку при вызове в сессии пользоввателя с переданным в него параметром uid");
    }

    /**
     *  Проверка на то, что метод выбросит ошибку если ввести невалидные параметры
     */

    @Test
    public void testMethodOnInvalidParams() throws ApiException {
        LOGGER.info("Проверим, что метод выдаст ответ по умолчанию, если ввести не валидный count");


        LOGGER.info("Вызовем метод в сессии пользователя");
        bindDefaultUserSession();
        GroupGetUserGroupsV2Response groupGetUserGroupsV2Response = okApi.getGroupService().getUserGroupsV2(-5);


        LOGGER.info("Проверим, что в ответе есть группы");
        Assert.assertNotNull("Метод вернул null", groupGetUserGroupsV2Response);
        Assert.assertNotNull("Метод вернул вместо groups null", groupGetUserGroupsV2Response.getGroups());
        Assert.assertFalse("Ответ пустой", groupGetUserGroupsV2Response.getGroups().isEmpty());
        Assert.assertNotNull("Метод вернул вместо anchor null", groupGetUserGroupsV2Response.getAnchor());
        Assert.assertFalse("Метод вернул пустой anchor", groupGetUserGroupsV2Response.getAnchor().isEmpty());

        LOGGER.info("Метод выдает ответ по умолчанию");
    }

}
