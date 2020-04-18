package win.doyto.i18n.module.group;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import win.doyto.i18n.common.TestConstant;
import win.doyto.query.service.PageList;
import win.doyto.query.web.response.ErrorCodeException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * GroupControllerTest
 *
 * @author f0rb on 2018-08-17.
 */
public class GroupControllerTest {

    public static final Integer INIT_I18N_SIZE = 8;
    public static final String NOISE_USER = "noise";
    private static final String FIELD_MESSAGE = "message";

    static {
        initGroups();
    }

    public static List<GroupEntity> initGroups() {
        List<GroupEntity> groupEntities = new ArrayList<>();
        for (int i = 0; i < INIT_I18N_SIZE; i++) {
            groupEntities.add(newGroup(i + ""));
        }
        groupEntities.add(newGroup("99", NOISE_USER));
        return groupEntities;
    }

    private GroupController groupController;

    private static GroupEntity newGroup(String suffix) {
        return newGroup(suffix, TestConstant.DEFAULT_USER);
    }

    private static GroupEntity newGroup(String suffix, String owner) {
        GroupEntity groupEntity = new GroupEntity();
        groupEntity.setCreateUserId(owner);
        groupEntity.setName("i18n" + suffix);
        groupEntity.setLabel("i18n多语言" + suffix);
        groupEntity.setCreateTime(new Date());
        groupEntity.setValid(true);
        groupEntity.setDeleted(false);
        return groupEntity;
    }

    @BeforeEach
    public void setUp() {
        GroupService groupService = new GroupService();
        groupService.batchInsert(initGroups());
        groupController = new GroupController(groupService);
    }

    @Test
    public void test_page() {
        int pageSize = 3;
        GroupQuery groupQuery = GroupQuery.builder().build();
        groupQuery.setPageNumber(1).setPageSize(pageSize);
        PageList<GroupResponse> page = groupController.page(TestConstant.DEFAULT_USER, groupQuery);
        assertThat(page.getTotal()).isEqualTo((long) INIT_I18N_SIZE);
        assertThat(page.getList())
                .hasSize(pageSize)
                .extracting(GroupResponse::getOwner)
                .containsOnly(TestConstant.DEFAULT_USER);

        PageList<GroupResponse> testUserPage = groupController.page(NOISE_USER, GroupQuery.builder().build());
        assertThat(testUserPage.getTotal()).isEqualTo(1);
        assertThat(testUserPage.getList())
            .hasSize(1)
            .extracting(GroupResponse::getOwner).containsExactly(NOISE_USER);
    }

    @Test
    public void test_updateLabel() {
        GroupRequest groupRequest = new GroupRequest();
        groupRequest.setId(1);
        String newLabelName = "test label";
        groupRequest.setLabel(newLabelName);
        try {
            groupController.updateLabel(groupRequest);
        } catch (Exception e) {
            fail(e.getMessage());
        }

        GroupResponse groupResponse = groupController.getById(1);
        assertThat(groupResponse).hasFieldOrPropertyWithValue("label", newLabelName);
    }

    @Test
    public void test_updateLabel_by_nonexistent_id() {
        GroupRequest groupRequest = new GroupRequest();
        groupRequest.setId(-1);
        groupRequest.setLabel("test label");

        try {
            groupController.updateLabel(groupRequest);
            fail();
        } catch (ErrorCodeException e) {
            assertThat(e.getErrorCode())
                .hasFieldOrPropertyWithValue("success", false)
                .hasFieldOrPropertyWithValue("code", 9)
                .hasFieldOrPropertyWithValue(FIELD_MESSAGE, "查询记录不存在");
        }

    }

    @Test
    public void test_delete() {
        groupController.delete("i18n", 1);

        try {
            groupController.getById(1);
            fail();
        } catch (ErrorCodeException e) {
            assertThat(e.getErrorCode())
                .hasFieldOrPropertyWithValue("success", false)
                .hasFieldOrPropertyWithValue("code", 9)
                .hasFieldOrPropertyWithValue(FIELD_MESSAGE, "查询记录不存在");
        }

        GroupQuery groupQuery = GroupQuery.builder().build();
        PageList<GroupResponse> page = groupController.page(TestConstant.DEFAULT_USER, groupQuery);
        assertThat(page.getTotal()).isEqualTo(INIT_I18N_SIZE - 1);
    }

    @Test
    public void test_delete_by_nonexistent_id() {
        try {
            groupController.delete("i18n", -1);
            fail();
        } catch (ErrorCodeException e) {
            assertThat(e.getErrorCode())
                .hasFieldOrPropertyWithValue("success", false)
                .hasFieldOrPropertyWithValue("code", 9)
                .hasFieldOrPropertyWithValue(FIELD_MESSAGE, "查询记录不存在");
        }
    }

}