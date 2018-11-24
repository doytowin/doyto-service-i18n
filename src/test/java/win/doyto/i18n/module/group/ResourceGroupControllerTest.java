package win.doyto.i18n.module.group;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import win.doyto.query.PageList;
import win.doyto.i18n.common.TestConstant;
import win.doyto.i18n.mapper.MockResourceGroupMapper;
import win.doyto.i18n.module.group.ResourceGroup;
import win.doyto.i18n.module.group.ResourceGroupController;
import win.doyto.i18n.module.group.ResourceGroupQuery;
import win.doyto.i18n.module.group.ResourceGroupService;
import win.doyto.web.RestError;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * ResourceGroupControllerTest
 *
 * @author f0rb on 2018-08-17.
 */
public class ResourceGroupControllerTest {

    public static final List<ResourceGroup> INIT_LIST = new ArrayList<>();
    public static final Integer INIT_I18N_SIZE = 8;
    public static final String NOISE_USER = "noise";

    static {
        for (int i = 0; i < INIT_I18N_SIZE; i++) {
            INIT_LIST.add(newResourceGroup(i + ""));
        }
        INIT_LIST.add(newResourceGroup("99", NOISE_USER));
    }

    private final MockResourceGroupMapper resourceGroupMapper = new MockResourceGroupMapper();
    private final ResourceGroupService groupService = new ResourceGroupService(resourceGroupMapper);
    private final ResourceGroupController resourceGroupController = new ResourceGroupController(groupService);

    private static ResourceGroup newResourceGroup(String suffix) {
        return newResourceGroup(suffix, TestConstant.DEFAULT_USER);
    }

    private static ResourceGroup newResourceGroup(String suffix, String owner) {
        ResourceGroup resourceGroup = new ResourceGroup();
        resourceGroup.setOwner(owner);
        resourceGroup.setName("i18n" + suffix);
        resourceGroup.setLabel("i18n多语言" + suffix);
        resourceGroup.setCreateTime(new Date());
        resourceGroup.setValid(true);
        resourceGroup.setDeleted(false);
        return resourceGroup;
    }

    @Before
    public void setUp() {
        resourceGroupMapper.reset();
        groupService.save(INIT_LIST);
    }

    @Test
    public void test_page() {
        Integer pageSize = 3;
        ResourceGroupQuery resourceGroupQuery = ResourceGroupQuery.builder().pageNumber("1").pageSize(pageSize + "").build();
        PageList<ResourceGroup> page = resourceGroupController.page(TestConstant.DEFAULT_USER, resourceGroupQuery);
        assertThat(page.getTotal()).isEqualTo((long) INIT_I18N_SIZE);
        assertThat(page.getList())
                .hasSize(pageSize)
                .extracting(ResourceGroup::getOwner)
                .containsOnly(TestConstant.DEFAULT_USER);

        PageList<ResourceGroup> testUserPage = resourceGroupController.page(
                NOISE_USER, ResourceGroupQuery.builder().build());
        assertThat(testUserPage.getTotal()).isEqualTo(1);
        assertThat(testUserPage.getList())
                .hasSize(1)
                .extracting(ResourceGroup::getOwner).containsExactly(NOISE_USER);
    }

    @Test
    public void test_updateLabel() {
        ResourceGroupController.UpdateGroupLabelRequest updateGroupLabelRequest
                = new ResourceGroupController.UpdateGroupLabelRequest();
        updateGroupLabelRequest.setId(1);
        String newLabelName = "test label";
        updateGroupLabelRequest.setLabel(newLabelName);
        RestError restError = resourceGroupController.updateLabel(updateGroupLabelRequest);
        assertTrue(restError.isSuccess());

        ResourceGroup resourceGroup = groupService.get(1);
        assertThat(resourceGroup).hasFieldOrPropertyWithValue("label", newLabelName);
    }

    @Test
    public void test_updateLabel_by_nonexistent_id() {
        ResourceGroupController.UpdateGroupLabelRequest updateGroupLabelRequest
                = new ResourceGroupController.UpdateGroupLabelRequest();
        updateGroupLabelRequest.setId(-1);
        updateGroupLabelRequest.setLabel("test label");
        RestError restError = resourceGroupController.updateLabel(updateGroupLabelRequest);
        assertThat(restError)
                .hasFieldOrPropertyWithValue("success", false)
                .hasFieldOrPropertyWithValue("code", 10002)
                .hasFieldOrPropertyWithValue("info", "记录不存在");
    }

    @Test
    public void test_delete() {
        RestError restError = resourceGroupController.delete(1);

        assertTrue(restError.isSuccess());

        ResourceGroup resourceGroup = groupService.get(1);
        assertThat(resourceGroup).hasFieldOrPropertyWithValue("deleted", true);

        ResourceGroupQuery resourceGroupQuery = new ResourceGroupQuery();
        PageList<ResourceGroup> page = resourceGroupController.page(TestConstant.DEFAULT_USER, resourceGroupQuery);
        assertThat(page.getTotal()).isEqualTo(INIT_I18N_SIZE - 1);
    }

    @Test
    public void test_delete_by_nonexistent_id() {
        RestError restError = resourceGroupController.delete(-1);
        assertThat(restError)
                .hasFieldOrPropertyWithValue("success", false)
                .hasFieldOrPropertyWithValue("code", 10002)
                .hasFieldOrPropertyWithValue("info", "记录不存在");
    }

}