package ru.odnoklassniki.tests.examples;

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
 */
public class TestUsersGetInfoBy extends ApiTestBase {
    private static final Logger LOGGER = Logger.getLogger(TestUsersGetInfoBy.class.getSimpleName());
    private static final String USER_ID = "572071876429";
    private static final String USER_NAME = "Юрий Дудь";
    private static final UserInfoField[] FIELDS = UserInfoField.values();

    /**
     * Пример теста на метод users.getInfoBy
     */
    @Test
    public void testUsersGetInfoByExample() throws ApiException {
        LOGGER.info("Получим данные о другом пользователе с помощью метода users.getInfoBy");
        bindDefaultUserSession();

        UsersGetInfoByResponse getInfoByResponse = okApi.getUserService().getInfoBy(USER_ID, FIELDS);

        LOGGER.info("Проверим, что получили верные данные");
        UserBean userBean = getInfoByResponse.getUser();
        Assert.assertNotNull("Не получили информацию о пользователе", userBean);
        Assert.assertEquals("Неверный ID пользователя", USER_ID, userBean.getUid());
        Assert.assertEquals("Неверное имя пользователя", USER_NAME, userBean.getName());
    }
}
