package homework;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.util.CollectionUtils;
import ru.odnoklassniki.base.ApiTestBase;
import ru.odnoklassniki.common.ApiException;
import ru.odnoklassniki.responses.group.GroupGetUserGroupsV2Response;
import ru.odnoklassniki.tests.examples.TestGroupGetUserGroupsV2;

import java.util.logging.Logger;

/**
 * Created by danny on 10.05.18 at 10:38.
 **/
public class TestUSersGetUserGroupsV2Homework extends ApiTestBase{
    private static final Logger LOGGER = Logger.getLogger(TestGroupGetUserGroupsV2.class.getSimpleName());
    @Test
    public void testGroupGetUserGroupsV2Example() throws ApiException {
        LOGGER.info("Получим данные о группах пользвоателя с помощью метода group.getUserGroupsV2");
        bindDefaultUserSession();

        GroupGetUserGroupsV2Response groupGetUserGroupsV2Response = okApi.getGroupService().getUserGroupsV2();
        LOGGER.info("Проверим, что в ответе есть группы");
        Assert.assertTrue("Больше чем 2 группы", groupGetUserGroupsV2Response.getGroups().size()>2);
        Assert.assertEquals("Есть группа с определенным id","53038939046008",groupGetUserGroupsV2Response.getGroups().get(1).getGroupId());
    }
}
