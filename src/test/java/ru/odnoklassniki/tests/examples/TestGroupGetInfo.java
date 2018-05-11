package ru.odnoklassniki.tests.examples;

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

    /**
     * Пример теста на метод group.getInfo
     */
    @Test
    public void testGroupGetInfoExample() throws ApiException {
        LOGGER.info("Получим данные об одной группе с помощью метода group.getInfo");
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
}
