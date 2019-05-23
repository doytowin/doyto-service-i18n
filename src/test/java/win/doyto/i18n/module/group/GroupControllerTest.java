package win.doyto.i18n.module.group;

import org.junit.Before;
import org.junit.Test;
import win.doyto.common.web.ErrorCodeException;
import win.doyto.i18n.common.TestConstant;
import win.doyto.query.core.PageList;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;

/**
 * GroupControllerTest
 *
 * @author f0rb on 2018-08-17.
 */
public class GroupControllerTest {

    public static final List<GroupEntity> INIT_LIST = new ArrayList<>();
    public static final Integer INIT_I18N_SIZE = 8;
    public static final String NOISE_USER = "noise";
    private static final String FIELD_MESSAGE = "message";

    static {
        for (int i = 0; i < INIT_I18N_SIZE; i++) {
            INIT_LIST.add(newGroup(i + ""));
        }
        INIT_LIST.add(newGroup("99", NOISE_USER));
    }

    private final MockGroupMapper mockGroupMapper = new MockGroupMapper();
    private final GroupService groupService = new GroupService(mockGroupMapper);
    private final GroupController groupController = new GroupController(groupService);

    private static GroupEntity newGroup(String suffix) {
        return newGroup(suffix, TestConstant.DEFAULT_USER);
    }

    private static GroupEntity newGroup(String suffix, String owner) {
        GroupEntity groupEntity = new GroupEntity();
        groupEntity.setOwner(owner);
        groupEntity.setName("i18n" + suffix);
        groupEntity.setLabel("i18n多语言" + suffix);
        groupEntity.setCreateTime(new Date());
        groupEntity.setValid(true);
        groupEntity.setDeleted(false);
        return groupEntity;
    }

    @Before
    public void setUp() {
        groupService.save(INIT_LIST);
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

        PageList<GroupResponse> testUserPage = groupController.page(
            NOISE_USER, GroupQuery.builder().build());
        assertThat(testUserPage.getTotal()).isEqualTo(1);
        assertThat(testUserPage.getList())
            .hasSize(1)
            .extracting(GroupResponse::getOwner).containsExactly(NOISE_USER);
    }

    @Test
    public void test_updateLabel() {
        GroupController.UpdateGroupLabelRequest updateGroupLabelRequest
                = new GroupController.UpdateGroupLabelRequest();
        updateGroupLabelRequest.setId(1);
        String newLabelName = "test label";
        updateGroupLabelRequest.setLabel(newLabelName);
        try {
            groupController.updateLabel(updateGroupLabelRequest);
        } catch (Exception e) {
            fail(e.getMessage());
        }


        GroupEntity groupEntity = groupService.get(1);
        assertThat(groupEntity).hasFieldOrPropertyWithValue("label", newLabelName);
    }

    @Test
    public void test_updateLabel_by_nonexistent_id() {
        GroupController.UpdateGroupLabelRequest updateGroupLabelRequest
                = new GroupController.UpdateGroupLabelRequest();
        updateGroupLabelRequest.setId(-1);
        updateGroupLabelRequest.setLabel("test label");

        try {
            groupController.updateLabel(updateGroupLabelRequest);
            fail();
        } catch (ErrorCodeException e) {
            assertThat(e.getErrorCode())
                .hasFieldOrPropertyWithValue("success", false)
                .hasFieldOrPropertyWithValue("code", 10002)
                .hasFieldOrPropertyWithValue(FIELD_MESSAGE, "查询记录不存在");
        }

    }

    @Test
    public void test_delete() {
        groupController.delete(1);

        GroupEntity groupEntity = groupService.get(1);
        assertThat(groupEntity).hasFieldOrPropertyWithValue("deleted", true);

        GroupQuery groupQuery = GroupQuery.builder().build();
        PageList<GroupResponse> page = groupController.page(TestConstant.DEFAULT_USER, groupQuery);
        assertThat(page.getTotal()).isEqualTo(INIT_I18N_SIZE - 1);
    }

    @Test
    public void test_delete_by_nonexistent_id() {
        try {
            groupController.delete(-1);
            fail();
        } catch (ErrorCodeException e) {
            assertThat(e.getErrorCode())
                .hasFieldOrPropertyWithValue("success", false)
                .hasFieldOrPropertyWithValue("code", 10002)
                .hasFieldOrPropertyWithValue(FIELD_MESSAGE, "查询记录不存在");
        }
    }

}