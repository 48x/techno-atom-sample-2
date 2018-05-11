package ru.odnoklassniki.homework.group;

import org.junit.Assert;
import org.junit.Test;
import ru.odnoklassniki.base.ApiTestBase;
import ru.odnoklassniki.common.ApiException;
import ru.odnoklassniki.common.group.GroupBean;
import ru.odnoklassniki.common.group.GroupBeanField;
import ru.odnoklassniki.tests.examples.TestGroupGetInfo;

import java.util.logging.Logger;


/**
 * Created by Maxim Vyshegorodtsev
 *
 * Чек-лист проверок api метода group.getInfo
 *
 * 1. Проверка на то, что сессия обязательна для внешних приложений
 *
 * 2. Проверка на то, что параметр uids обязателен
 *
 * 3. Проверка на то, что параметр fields обязателен
 *
 * 4. Проверка на то, что метод выбросит ошибку, если ввести невалидные параметры
 *
 * 5. Проверка на то, что метод корректно отработает, если его вызвать с параметрами uids, fields (по дефоолту)
 *
 * 6. Проверка на то, что переданный в метод параметр move_to_top имеет смысл, только если запрашиваемая группа единственная
 */

public class TestGetInfo extends ApiTestBase {
    private static final Logger LOGGER = Logger.getLogger(TestGroupGetInfo.class.getSimpleName());
    private static final String ERROR_NO_SESSION = "PARAM : Missed required parameter: access_token";
    private static final String ERROR_NO_UIDS = "PARAM : Missing required parameter uids";
    private static final String ERROR_NO_FIELDS = "PARAM : Missing required parameter fields";
    private static final String ERROR_INVALID = "PARAM : Invalid";
    private static final String GROUP_ID = "53245288710321";
    private static final GroupBeanField[] FIELDS = new GroupBeanField[]{GroupBeanField.UID, GroupBeanField.NAME};
    private static final String GROUP_NAME = "Технополис OK (Mail. Ru) и Политеха";
    private static final String ERROR_MOVE_TO_TOP_PARAM = "PARAM : Param 'move_to_top' can be true for single group only";
    private static final String[] GROUP_IDS = new String[]{"3357785645192", "51722779689102"};

    /**
     * Проверка на то, что сессия обязательна для внешних приложений
     */
    @Test
    public void testMethodThrowsExceptionWhenSessionIsNotSet() {
        LOGGER.info("Проверим, что метод сессионный и требует указания access_token");

        try {
            LOGGER.info("Вызовем метод без сессии");
            okApi.getGroupService().getGroupInfo(new String[]{}, new GroupBeanField[]{});
            Assert.fail("Метод не кинул ошибку при отсутствии пользовательской сессии");
        } catch (ApiException e) {
            LOGGER.info("Получили ошибку, проверим её текст");
            Assert.assertEquals("Неверная ошибка при вызове без сессии", ERROR_NO_SESSION, e.getApiErrorInfo().getErrorMessage());
        }
        LOGGER.info("Метод сессионный и кидает верную ошибку при вызове без сессии");
    }

    /**
     * Проверка на то, что параметр uids обязателен
     */

    @Test
    public void testParamUidsIsRequired() {
        LOGGER.info("Проверим, что метод выбросит исключение, если не указать uids");

        try {
            LOGGER.info("Вызовем метод в сессии пользователя");
            bindDefaultUserSession();
            okApi.getGroupService().getGroupInfo(new String[]{}, new GroupBeanField[]{});
            Assert.fail("Метод не кинул исключение при остутствии uids");
        } catch (ApiException e) {
            LOGGER.info("Получили ошибку, проверим её сообщение");
            Assert.assertEquals("Неверная ошибка при вызове без uids", ERROR_NO_UIDS, e.getApiErrorInfo().getErrorMessage());
        }

        LOGGER.info("Параметр uids обязательный");
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
            okApi.getGroupService().getGroupInfo(new String[]{GROUP_ID}, new GroupBeanField[]{});
            Assert.fail("Метод не кинул ошибку при отсутствии fields");
        } catch (ApiException e) {
            LOGGER.info("Получили ошибку, проверим её сообщение");
            Assert.assertEquals("Неверная ошибка при вызове без fields", ERROR_NO_FIELDS, e.getApiErrorInfo().getErrorMessage());
        }

        LOGGER.info("Параметр fields обязателен");
    }

    /**
     * Проверка на то, что метод выбросит ошибку, если ввести невалидные параметры
     */

    @Test
    public void testMethodOnInvalidParams() {
        LOGGER.info("Проверим, что метод выкинет ошибку, если не ввести невалидные данные");

        try {
            LOGGER.info("Вызовем метод в сессии пользователя");
            bindDefaultUserSession();
            okApi.getGroupService().getGroupInfo(new String[]{"hjsdhfjsdh"}, FIELDS);
            Assert.fail("Метод не кинул ошибку при невалидных параметрах");
        } catch (ApiException e) {
            LOGGER.info("Получили ошибку, проверим её сообщение");
            Assert.assertTrue("Неверная ошибка при вызове метода с невалидными параметрами",
                    e.getApiErrorInfo().getErrorMessage().startsWith(ERROR_INVALID));
        }

        LOGGER.info("Метод кидает ошибки при вводе невалидных параметров");
    }

    /**
     * Проверка на то, что метод корректно отработает, если его вызвать с параметрами uids, fields (по дефоолту)
     */

    @Test
    public void testMethodWithValidUidsAndFields() throws ApiException {
        LOGGER.info("Проверим, что метод корректно отработает с параметрами uids, fields");

        LOGGER.info("Вызовем метод в сессии пользователя");
        bindDefaultUserSession();

        GroupBean[] groupBeans = okApi.getGroupService().getGroupInfo(new String[]{GROUP_ID}, FIELDS);

        LOGGER.info("Проверим, что в ответе нужные данные о группе");
       Assert.assertNotNull("Метод выдал null", groupBeans);
       Assert.assertEquals("Неверное количество групп в ответе", groupBeans.length, 1);

        Assert.assertEquals("Неверный id группы", GROUP_ID, groupBeans[0].getUid());
        Assert.assertEquals("Неверное название группы", GROUP_NAME, groupBeans[0].getName());

        LOGGER.info("Метод корректно отработал при введенных uids, fields");
    }

    /**
     * Проверка на то, что переданный в метод параметр move_to_top имеет смысл, только если запрашиваемая группа единственная
     */

    @Test
    public void testMethodWithUidsAndFieldsAndMoveToTop() throws ApiException {
        LOGGER.info("Проверим, что метод кидает ошибку, если передать move_to_top = true и запросить больше одной группы");

        try {
            LOGGER.info("Вызовем метод в сессии пользователя");
            bindDefaultUserSession();
            okApi.getGroupService().getGroupInfo(GROUP_IDS, FIELDS, true);
            Assert.fail("Метод не выкинул ошибку");
        }
        catch (ApiException e) {
            LOGGER.info("Получиили ошибку, проверим её сообщение");
            Assert.assertEquals("Неверная ошибка", ERROR_MOVE_TO_TOP_PARAM, e.getApiErrorInfo().getErrorMessage());
        }

        LOGGER.info("Параметр move_to_top имеет смысл только для одной запрашиваемой группы");
    }
}
