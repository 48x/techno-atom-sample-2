package ru.odnoklassniki.tests.examples;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.util.CollectionUtils;
import ru.odnoklassniki.base.ApiTestBase;
import ru.odnoklassniki.common.ApiException;
import ru.odnoklassniki.responses.group.GroupGetUserGroupsV2Response;

import java.util.logging.Logger;

/**
 * Created by Soldatov Artem as demotests
 */
public class TestGroupGetUserGroupsV2 extends ApiTestBase {
    private static final Logger LOGGER = Logger.getLogger(TestGroupGetUserGroupsV2.class.getSimpleName());

    @Test
    public void testGroupGetUserGroupsV2Example() throws ApiException {
        LOGGER.info("Get user groups info");
        bindDefaultUserSession();

        GroupGetUserGroupsV2Response groupGetUserGroupsV2Response = okApi.getGroupService().getUserGroupsV2();
        Assert.assertFalse("Empty group list", CollectionUtils.isEmpty(groupGetUserGroupsV2Response.getGroups()));
        LOGGER.info("group data obtained");
    }

    @Test
    public void testUserSessionIsRequired() {
        LOGGER.info("try to get info without session");
        try {
            okApi.getGroupService().getUserGroupsV2();
            Assert.fail("Method did not throw exception without session");
        } catch (ApiException e) {
            Assert.assertEquals("Session Is Required", ERROR_NO_SESSION, e.getApiErrorInfo().getErrorMessage());
        }

        LOGGER.info("session method throws a correct error");
    }

    @Test
    public void testCountParameter() throws ApiException {
        LOGGER.info("Check that the number of groups is the same as count");
        int count = 5;

        bindDefaultUserSession();
        GroupGetUserGroupsV2Response groupGetUserGroupsV2Response = okApi.getGroupService().getUserGroupsV2(count);

        Assert.assertEquals("the number of groups is not the same as count", groupGetUserGroupsV2Response.getGroups().size(), count);

        LOGGER.info("the number of groups is the same as count");
    }

}