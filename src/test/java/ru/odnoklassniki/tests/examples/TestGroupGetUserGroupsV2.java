package ru.odnoklassniki.tests.examples;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.util.CollectionUtils;
import ru.odnoklassniki.base.ApiTestBase;
import ru.odnoklassniki.common.ApiException;
import ru.odnoklassniki.responses.group.GroupGetUserGroupsV2Response;

import java.util.logging.Logger;

/**
 * Created by Maksim Egorichev on 5/9/18 at 4:39 PM
 */
public class TestGroupGetUserGroupsV2 extends ApiTestBase {
    private static final Logger LOGGER = Logger.getLogger(TestGroupGetUserGroupsV2.class.getSimpleName());

    /**
     * Пример теста на метод group.getUserGroupsV2

    @Test
    public void testGroupGetUserGroupsV2Example() throws ApiException {
        LOGGER.info("Получим данные о группах пользвоателя с помощью метода group.getUserGroupsV2");
        bindDefaultUserSession();

        GroupGetUserGroupsV2Response groupGetUserGroupsV2Response = okApi.getGroupService().getUserGroupsV2();

        LOGGER.info("Проверим, что в ответе есть группы");
        Assert.assertFalse("Пустой список групп", CollectionUtils.isEmpty(groupGetUserGroupsV2Response.getGroups()));
    }

     * Мои тесты group.getUserGroupsV2
     */

    @Test
   public void testGroupGetUserGroupsV2My() throws ApiException {
                   LOGGER.info("Получим данные о группах пользвоателя с помощью метода group.getUserGroupsV2");
                    bindDefaultUserSession();

                    GroupGetUserGroupsV2Response groupGetUserGroupsV2Response = okApi.getGroupService().getUserGroupsV2();
                    LOGGER.info("Проверим, что в ответе есть группы");
                   Assert.assertEquals("Совпадает userId","561744237296",groupGetUserGroupsV2Response.getGroups().get(1).getUserId());
                   Assert.assertNotNull("Количество групп не 0",groupGetUserGroupsV2Response.getGroups().size());
                   Assert.assertFalse("Пустой список групп",groupGetUserGroupsV2Response.getGroups().isEmpty());
                   Assert.assertTrue("Содержит инофрмацию Anchor",groupGetUserGroupsV2Response.getGroups().contains(groupGetUserGroupsV2Response.getAnchor()));
    }
}
