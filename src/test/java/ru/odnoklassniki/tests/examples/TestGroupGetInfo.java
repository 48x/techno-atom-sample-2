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
 * Пример теста на метод group.getInfo
 */
public class TestGroupGetInfo extends ApiTestBase {
    private static final Logger LOGGER = Logger.getLogger(TestGroupGetInfo.class.getSimpleName());
    private static final String GROUP_ID = "53245288710321";
    private static final String GROUP_NAME = "Технополис OK (Mail. Ru) и Политеха";
    private static final GroupBeanField[] FIELDS = new GroupBeanField[]{GroupBeanField.UID, GroupBeanField.NAME};
    private static final String ERROR_NO_SESSION = "PARAM : Missed required parameter: access_token";
    private static final String INVALID_GROUP_ID = "BlaBlaBla";
    private static final String ERROR_ID_IS_INVALID = "PARAM : Invalid UID value BlaBlaBla";
    private static final String ERROR_ID_IS_NOT_SET = "PARAM : Missing required parameter uids";
    private static final String ERROR_FIELDS_IS_NOT_SET = "PARAM : Missing required parameter fields";


    @Test
    public void testGetInfoReturnCorrectData() throws ApiException {
        LOGGER.info("Проверим, что метод возвращает правильные данные для корректного id и списка полей");
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
    }

    @Test
    public void testFieldsIsCorrect() throws ApiException {
        LOGGER.info("Проверим, что метод возвращает только нужные поля");
        bindDefaultUserSession();

        GroupBean[] groupBeans = okApi.getGroupService().getGroupInfo(new String[]{GROUP_ID}, FIELDS);
        GroupBean groupBean = groupBeans[0];


        LOGGER.info("Проверим, что нужные поля есть");
        Assert.assertTrue("Пустой id", StringUtils.isNotBlank(groupBean.getUid()));
        Assert.assertTrue("Пустое имя", StringUtils.isNotBlank(groupBean.getName()));

        LOGGER.info("Проверим, что ненужных полей нет");
        Assert.assertNull("Непустой description",groupBean.getDescription());
        Assert.assertNull("Непустой shortname",groupBean.getShortname());
        Assert.assertNull("Непустой status",groupBean.getStatus());

    }

    @Test
    public void testGetInfoThrowsExceptionWhenSessionIsNotSet() {
        LOGGER.info("Проверим, что метод сессионный и требует указания access_token");

        try {
            LOGGER.info("Вызовем метод без сессии");
            okApi.getGroupService().getGroupInfo(new String[]{GROUP_ID},FIELDS);
            Assert.fail("Метод не кинул ошибку при отсутствии пользовательской сессии");
        } catch (ApiException e) {
            LOGGER.info("Получили ошибку, проверим её текст");
            Assert.assertEquals("Неверная ошибка при вызове без сессии", ERROR_NO_SESSION, e.getApiErrorInfo().getErrorMessage());
        }
    }

    @Test
    public void  testGetInfoTrowsExceptionWhenIdIsIncorrect() throws ApiException {
        LOGGER.info("Проверим, что метод выбрасывает исключение, когда id не валидный");
        bindDefaultUserSession();

        try {
            LOGGER.info("Вызовем метод с невалидным id");
            okApi.getGroupService().getGroupInfo(new String[]{INVALID_GROUP_ID},FIELDS);
            Assert.fail("Метод не кинул ошибку с невалидным id");
        } catch (ApiException e) {
            LOGGER.info("Получили ошибку, проверим её текст");
            Assert.assertEquals("Неверная ошибка при вызове без сессии", ERROR_ID_IS_INVALID, e.getApiErrorInfo().getErrorMessage());
        }
    }

    @Test
    public void  testGetInfoTrowsExceptionWhenIdIsNotSet() throws ApiException {
        LOGGER.info("Проверим, что метод выбрасывает исключение, когда id не установлен");
        bindDefaultUserSession();

        try {
            LOGGER.info("Вызовем метод без id");
            okApi.getGroupService().getGroupInfo(new String[]{},FIELDS);
            Assert.fail("Метод не кинул ошибку при отсутствии id");
        } catch (ApiException e) {
            LOGGER.info("Получили ошибку, проверим её текст");
            Assert.assertEquals("Неверная ошибка при вызове без сессии", ERROR_ID_IS_NOT_SET, e.getApiErrorInfo().getErrorMessage());
        }
    }


    @Test
    public void testGetInfoThrowsExceptionWhenFieldsNotSet() throws ApiException {
        LOGGER.info("Проверим, что метод сессионный и требует указания access_token");
        bindDefaultUserSession();

        try {
            LOGGER.info("Вызовем метод без списка полей");
            okApi.getGroupService().getGroupInfo(new String[]{GROUP_ID},null);
            Assert.fail("Метод не кинул ошибку при отсутствии списка полей");
        } catch (ApiException e) {
            LOGGER.info("Получили ошибку, проверим её текст");
            Assert.assertEquals("Неверная ошибка при вызове без сессии", ERROR_FIELDS_IS_NOT_SET, e.getApiErrorInfo().getErrorMessage());
        }
    }

}
