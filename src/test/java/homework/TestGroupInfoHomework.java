package homework;

import org.junit.Assert;
import org.junit.Test;
import ru.odnoklassniki.base.ApiTestBase;
import ru.odnoklassniki.common.ApiException;
import ru.odnoklassniki.common.group.GroupBean;
import ru.odnoklassniki.common.group.GroupBeanField;
import ru.odnoklassniki.tests.examples.TestGroupGetInfo;

import java.util.logging.Logger;


/**
 * Created by danny on 10.05.18 at 10:51.
 **/
public class TestGroupInfoHomework extends ApiTestBase{
    private static final Logger LOGGER = Logger.getLogger(TestGroupGetInfo.class.getSimpleName());
    private static final String GROUP_ID = "52631054123215";
    private static final String GROUP_NAME = "МИР ПОЗИТИВА";
    private static final GroupBeanField[] FIELDS = new GroupBeanField[]{GroupBeanField.UID, GroupBeanField.NAME,GroupBeanField.DESCRIPTION,GroupBeanField.MEMBERS_COUNT,GroupBeanField.CITY};

    /**
     * Пример теста на метод group.getInfo
     */
    @Test
    public void testGroupGetInfoExample() throws ApiException {
        LOGGER.info("Получим данные об одной группе с помощью метода group.getInfo");
        bindDefaultUserSession();

        GroupBean[] groupBeans = okApi.getGroupService().getGroupInfo(new String[]{GROUP_ID}, FIELDS);

        GroupBean groupBean = groupBeans[0];
        Assert.assertEquals("Невернок описание","ВСЁ ДЛЯ ХОРОШЕГО НАСТРОЕНИЯ", groupBean.getDescription());
        Assert.assertTrue("Количество участников неверно или уменьшилось",groupBean.getMembersCount()>677500);
        Assert.assertEquals("Неверный город","Гомель",groupBean.getCity());
        Assert.assertEquals("Неверный id группы", GROUP_ID, groupBean.getUid());
        Assert.assertEquals("Неверное название группы", GROUP_NAME, groupBean.getName());
    }
}
