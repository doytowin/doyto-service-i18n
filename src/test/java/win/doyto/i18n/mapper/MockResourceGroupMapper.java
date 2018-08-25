package win.doyto.i18n.mapper;

import java.util.function.Predicate;

import org.apache.commons.lang3.StringUtils;
import win.doyto.common.repository.mock.AbstractMockMapper;
import win.doyto.i18n.module.group.ResourceGroup;
import win.doyto.i18n.module.group.ResourceGroupMapper;
import win.doyto.i18n.module.group.ResourceGroupQuery;

/**
 * MockResourceGroupMapper
 *
 * @author f0rb on 2018-08-22.
 */
public class MockResourceGroupMapper extends AbstractMockMapper<ResourceGroup, Integer, ResourceGroupQuery> implements ResourceGroupMapper {

    public MockResourceGroupMapper() {
        super(ResourceGroup.TABLE);
    }

    @Override
    public ResourceGroup getByName(String owner, String name) {
        return entitiesMap.values().stream()
                .filter(filterByOwnerAndGroupName(owner, name))
                .findFirst().orElse(null);
    }

    private Predicate<ResourceGroup> filterByOwnerAndGroupName(String owner, String name) {
        return resourceGroup -> StringUtils.equals(resourceGroup.getOwner(), owner) && StringUtils.equals(resourceGroup.getName(), name);
    }
}
