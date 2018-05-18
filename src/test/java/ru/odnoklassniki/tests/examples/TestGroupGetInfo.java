package ru.odnoklassniki.tests.examples;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;
import ru.odnoklassniki.base.ApiTestBase;
import ru.odnoklassniki.common.ApiException;
import ru.odnoklassniki.common.group.GroupBean;
import ru.odnoklassniki.common.group.GroupBeanField;

import java.util.logging.Logger;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.arrayWithSize;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

/**
 * Created by Maksim Egorichev on 5/9/18 at 4:01 PM
 *
 * Updated by Dmitriy Tcibisov on 5/18/18 at 15:25 PM
 *
 * Тесты на метод group.getInfo
 *
 * Чек-лист:
 * 1. Проверка, что метод сессионный и требует указания access_token
 * 2. Проверка, что метод выполняется без ошибок при корректных параметрах uids и fields
 * 3. Проверка, что метод выбрасывает исключение при некорректном параметре uids
 * 4. Проверка, что метод выбрасывает исключение при отсутствии обязательного параметра uids
 * 5. Проверка, что метод выбрасывает исключение при отсутствии обязательного параметра fields
 * 6. Проверка, что учитывается параметр fields и в ответе возвращаются только запрошенные поля
 * 7. Проверка, что метод выбрасывает исключение при параметре move_to_top = true и более чем одной группе в параметре uids
 */
public class TestGroupGetInfo extends ApiTestBase {
    private static final Logger LOGGER = Logger.getLogger(TestGroupGetInfo.class.getSimpleName());
    private static final String UNCORRECT_GROUP_ID = "Incorrect";
    private static final String GROUP_ID = "53245288710321";
    private static final String GROUP_NAME = "Технополис OK (Mail. Ru) и Политеха";
    private static final GroupBeanField[] FIELDS = new GroupBeanField[]{GroupBeanField.UID, GroupBeanField.NAME};
    private static final String ERROR_INCORRECT_UIDS = "PARAM : Invalid UID value";
    private static final String ERROR_NO_SESSION = "PARAM : Missed required parameter: access_token";
    private static final String ERROR_NO_UIDS = "PARAM : Missing required parameter uids";
    private static final String ERROR_NO_FIELDS = "PARAM : Missing required parameter fields";
    private static final String ERROR_INCORRECT_USE_MOVE_TO_TOP = "PARAM : Param 'move_to_top' can be true for single group only";

    /**
     * 1. Проверка, что метод сессионный и требует указания access_token
     */
    @Test
    public void testMethodThrowsExceptionWhenSessionIsNotSet() {
        LOGGER.info("Проверим, что метод сессионный и требует указания access_token");
        try {
            LOGGER.info("Вызовем метод без сессии");
            okApi.getGroupService().getGroupInfo(new String[]{GROUP_ID}, FIELDS);
            Assert.fail("Метод не кинул ошибку при отсутствии пользовательской сессии");
        } catch (ApiException e) {
            LOGGER.info("Получили ошибку, проверим её текст");
            Assert.assertEquals("Неверная ошибка при вызове без сессии", ERROR_NO_SESSION, e.getApiErrorInfo().getErrorMessage());
        }
        LOGGER.info("Метод сессионный и кидает верную ошибку при вызове без сессии");
    }

    /**
     * 2. Проверка, что метод выполняется без ошибок при корректных параметрах uids и fields
     */
    @Test
    public void testMethodNoThrowsExceptionWhenUidsAndFieldsAreCorrect() throws ApiException {
        LOGGER.info("Проверим, что метод отдаёт верные данные при корректных uids и fields");
        bindDefaultUserSession();

        GroupBean[] groupBeans = okApi.getGroupService().getGroupInfo(new String[]{GROUP_ID}, FIELDS);

        LOGGER.info("Проверим, что в ответе нужные данные о группе");
        Assert.assertThat(
                "Неверное количество групп в ответе",
                groupBeans,
                allOf(
                        notNullValue(),
                        arrayWithSize(equalTo(1))
                )
        );

        GroupBean groupBean = groupBeans[0];
        Assert.assertEquals("Неверный id группы", GROUP_ID, groupBean.getUid());
        Assert.assertEquals("Неверное название группы", GROUP_NAME, groupBean.getName());

        LOGGER.info("Метод выполняется без ошибок и отдаёт корректные данные при введённых uids и fields");
    }

    /**
     * 3. Проверка, что метод выбрасывает исключение при некорректном параметре uids
     */
    @Test
    public void testMethodThrowsExceptionWhenUidsIsIncorrect() {
        LOGGER.info("Проверим, что метод выбрасывает исключение при некорректном uids");
        try {
            LOGGER.info("Вызовем метод в сессии пользователя");
            bindDefaultUserSession();
            LOGGER.info("Вызовем метод с некорректным uids");
            okApi.getGroupService().getGroupInfo(new String[]{UNCORRECT_GROUP_ID}, FIELDS);
            Assert.fail("Метод не кинул ошибку при некорректном uids");
        }
        catch (ApiException e) {
            LOGGER.info("Получили ошибку, проверим её текст");
            Assert.assertTrue("Неверная ошибка при вызове с некорректным uids",
                    e.getApiErrorInfo().getErrorMessage().startsWith(ERROR_INCORRECT_UIDS));
        }
        LOGGER.info("Метод ожидает корректные uids и выбрасывает верную ошибку при вызове с некорректным uids");
    }

    /**
     * 4. Проверка, что метод выбрасывает исключение при отсутствии обязательного параметра uids
     */
    @Test
    public void testMethodThrowsExceptionWhenUidIsNotSend() {
        LOGGER.info("Проверим, что метод требует указания uids");
        try {
            LOGGER.info("Вызовем метод в сессии пользователя");
            bindDefaultUserSession();
            LOGGER.info("Вызовем метод без указания uids");
            okApi.getGroupService().getGroupInfo(null, FIELDS);
            Assert.fail("Метод не кинул ошибку при отсутствии uids");
        } catch (ApiException e) {
            LOGGER.info("Получили ошибку, проверим её текст");
            Assert.assertEquals("Неверная ошибка при вызове без указания uids", ERROR_NO_UIDS, e.getApiErrorInfo().getErrorMessage());
        }
        LOGGER.info("Метод ожидает uids и кидает верную ошибку при вызове без указания uids");
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
            okApi.getGroupService().getGroupInfo(new String[]{GROUP_ID}, null);
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

        GroupBean[] groupBeans = okApi.getGroupService().getGroupInfo(new String[]{GROUP_ID}, new GroupBeanField[]{GroupBeanField.NAME});
        GroupBean groupBean = groupBeans[0];

        LOGGER.info("Проверим, что в ответе есть запрошенное поле");
        Assert.assertTrue("Пустое название группы", StringUtils.isNotBlank(groupBean.getName()));
        LOGGER.info("Проверим, что в ответе нет незапрошенного поля");
        Assert.assertNull("Не пустое id группы", groupBean.getUid());

        LOGGER.info("Учитывается параметр fields и в ответе возвращаются только запрошенные поля");
    }

    /**
     * 7. Проверка, что метод выбрасывает исключение при параметре move_to_top = true и более чем одной группе в параметре uids
     */
    @Test
    public void testMethodThrowsExceptionWhenMoveToTopAndManyOfUidsIsSend() {
        LOGGER.info("Проверим, что метод выбрасывает исключение при вызове move_to_top с более чем одной группой");
        try {
            LOGGER.info("Вызовем метод в сессии пользователя");
            bindDefaultUserSession();
            LOGGER.info("Вызовем метод с move_to_top и более чем одной группой");
            okApi.getGroupService().getGroupInfo(new String[]{GROUP_ID, GROUP_ID}, FIELDS, true);
            Assert.fail("Метод не кинул ошибку при вызове move_to_top с более чем одной группой");
        }
        catch (ApiException e) {
            LOGGER.info("Получили ошибку, проверим её текст");
            Assert.assertEquals("Неверная ошибка при вызове move_to_top с более чем одной группой",
                    e.getApiErrorInfo().getErrorMessage(), ERROR_INCORRECT_USE_MOVE_TO_TOP);
        }
        LOGGER.info("Метод ожидает один uid при использовании параметра move_to_top = true и выбрасывает верную ошибку в противном случае");
    }

}
