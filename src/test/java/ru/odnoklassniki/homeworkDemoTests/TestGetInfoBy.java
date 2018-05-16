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
 * Created by Soldatov Artem as demotests
 */
public class TestUsersGetInfoBy extends ApiTestBase {
    private static final Logger LOGGER = Logger.getLogger(TestUsersGetInfoBy.class.getSimpleName());
    private static final String USER_ID = "58456218710542";
    private static final String USER_NAME = "Donald Trump";
    private static final UserInfoField[] FIELDS = UserInfoField.values();

    @Test
    public void testUsersGetInfoWithValidData() throws ApiException {
        LOGGER.info("try to get data with correct uid and field");
        bindDefaultUserSession();

        UsersGetInfoByResponse getInfoByResponse = okApi.getUserService().getInfoBy(USER_ID, FIELDS);

        UserBean userBean = getInfoByResponse.getUser();
        Assert.assertNotNull("no user data", userBean);
        Assert.assertEquals("invalid user id", USER_ID, userBean.getUid());
        Assert.assertEquals("invalid user name", USER_NAME, userBean.getName());
    }

    @Test
    public void testUserSessionIsRequired() throws ApiException {
        LOGGER.info("try to get info without session");
        try {
            GroupBean[] groupBeans = okApi.getUserService().getInfoBy(USER_ID, FIELDS);
            Assert.fail("Method did not throw exception without session");
        } catch (IllegalArgumentException e) {
            Assert.assertEquals("Session Is Required", e.getApiErrorInfo().getErrorMessage());
        }
    }
    }
}