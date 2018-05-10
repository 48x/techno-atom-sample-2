package homework;

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
public class TestUsersGetInfoByhomework extends ApiTestBase {
    private static final Logger LOGGER = Logger.getLogger(TestUsersGetInfoByhomework.class.getSimpleName());
    private static final String USER_ID = "571419061118";
    private static final String USER_NAME = "No Name";
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
        Assert.assertTrue("Совершеннолетний пользователь",userBean.getAge()>18);
        Assert.assertEquals("Русскоязычный пользователь","ru",userBean.getLocale());

    }
}
