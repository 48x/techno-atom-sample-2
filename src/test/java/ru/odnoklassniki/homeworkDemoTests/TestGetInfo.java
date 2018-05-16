package ru.odnoklassniki.homeworkDemoTests;

import org.junit.Assert;
import org.junit.Test;
import ru.odnoklassniki.base.ApiTestBase;
import ru.odnoklassniki.common.ApiException;
import ru.odnoklassniki.common.group.GroupBean;
import ru.odnoklassniki.common.group.GroupBeanField;

/**
 * Created by Soldatov Artem as demotests
 */

public class TestGetInfo extends ApiTestBase {
    private static final Logger LOGGER = Logger.getLogger(TestGroupGetInfo.class.getSimpleName());
    private static final String GROUP_ID = "58456218710542";
    private static final String GROUP_NAME = "Test Group Name 1";
    private static final GroupBeanField[] FIELDS = new GroupBeanField[]{GroupBeanField.UID, GroupBeanField.NAME};


    @Test
    public void testMethodWithValidData() {
        LOGGER.info("check metod returns a collection of the correct length");

        bindDefaultUserSession();

        GroupBean[] groupBeans = okApi.getGroupService().getGroupInfo(new String[]{GROUP_ID}, FIELDS);

        Assert.assertThat(
                "Incorrect size of the collection",
                groupBeans,
                allOf(
                        notNullValue(),
                        arrayWithSize(equalTo(1))
                )
        );

        LOGGER.info("collection has correct length");
    }

    @Test
    public void testParamUidsIsRequired() {
        LOGGER.info("check exception in the absence of uids");
        bindDefaultUserSession();
        try {
            GroupBean[] groupBeans = okApi.getGroupService().getGroupInfo(new String[]{}, FIELDS);
            Assert.fail("Method did not throw exception in the absence of uids");
        } catch (IllegalArgumentException e) {
            Assert.assertEquals("PARAM : Missing required parameter uids", e.getApiErrorInfo().getErrorMessage());
        }
        LOGGER.info("exception in the absence of uids correct");
    }

    @Test
    public void testParamUidsIsRequired() {
        LOGGER.info("check empty fields working");
        bindDefaultUserSession();
        try {
            okApi.getGroupService().getGroupInfo(new String[]{GROUP_ID}, new GroupBeanField[]{});
            Assert.fail("Method did not throw exception in the absence of fields");
        } catch (IllegalArgumentException e) {
            Assert.assertEquals("PARAM : Missing required parameter fields", e.getApiErrorInfo().getErrorMessage());
        }
        LOGGER.info("exception in the absence of fields correct");
    }


}
