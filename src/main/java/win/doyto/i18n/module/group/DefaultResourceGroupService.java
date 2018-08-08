package win.doyto.i18n.module.group;

import java.util.Date;
import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import win.doyto.common.repository.mybatis.AbstractMyBatisDataService;
import win.doyto.i18n.exception.RestNotFoundException;
import win.doyto.i18n.module.i18n.I18nService;
import win.doyto.web.RestEnum;
import win.doyto.web.RestError;

/**
 * GroupDefaultService
 *
 * @author f0rb on 2017-03-29.
 */
@Slf4j
@Service
public class DefaultResourceGroupService
    extends AbstractMyBatisDataService<ResourceGroup, Integer, ResourceGroupQuery>
        implements ResourceGroupService {

    @Autowired
    public DefaultResourceGroupService(ResourceGroupMapper resourceGroupMapper) {
        this.resourceGroupMapper = resourceGroupMapper;
    }

    private ResourceGroupMapper resourceGroupMapper;

    @Resource
    private I18nService i18nService;

    @Override
    public ResourceGroupMapper getMapper() {
        return resourceGroupMapper;
    }

    @Override
    public ResourceGroup getGroup(String user, String groupName) {
        ResourceGroup group = resourceGroupMapper.getByName(user, groupName);
        if (group == null) {
            throw new RestNotFoundException("资源分组未配置: " + groupName);
        }
        return group;
    }

    @Override
    public RestError deleteByUser(String user, Integer groupId) {
        ResourceGroup origin = resourceGroupMapper.get(groupId);
        if (origin == null) {
            return RestEnum.RecordNotFound.value();
        }
        origin.setValid(false);
        resourceGroupMapper.update(origin);
        return RestError.create(0, " 资源组删除成功: " + groupId);
    }

    @Override
    @Transactional
    public RestError create(String owner, String name, String label, String locale) {
        ResourceGroup resourceGroup = resourceGroupMapper.getByName(owner, name);
        if (resourceGroup != null) {
            return RestEnum.RecordAlreadyExists.value();
        }
        resourceGroup = new ResourceGroup();
        resourceGroup.setOwner(owner);
        resourceGroup.setName(name);
        resourceGroup.setLabel(label);
        resourceGroup.setCreateTime(new Date());
        resourceGroupMapper.insert(resourceGroup);
        i18nService.createGroupTable(owner, name);
        i18nService.addLocaleOnGroup(owner, name, locale);
        return RestEnum.Success.value();
    }

    @Override
    public RestError updateLabel(Integer groupId, String label) {
        ResourceGroup origin = resourceGroupMapper.get(groupId);
        if (origin == null) {
            return RestEnum.RecordNotFound.value();
        }
        origin.setLabel(label);
        origin.setUpdateTime(new Date());
        resourceGroupMapper.update(origin);
        return RestEnum.SuccessUpdate.value();
    }

    @Override
    public ResourceGroup save(ResourceGroup resourceGroup) {
        return null;
    }

}