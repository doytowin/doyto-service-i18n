package win.doyto.i18n.module.group;

import java.util.Date;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import win.doyto.query.mybatis.AbstractMyBatisDataService;
import win.doyto.i18n.exception.RestNotFoundException;
import win.doyto.web.RestEnum;
import win.doyto.web.RestError;

/**
 * GroupDefaultService
 *
 * @author f0rb on 2017-03-29.
 */
@Slf4j
@Service
public class ResourceGroupService
        extends AbstractMyBatisDataService<ResourceGroup, Integer, ResourceGroupQuery>
         {

    public ResourceGroupService(ResourceGroupMapper resourceGroupMapper) {
        this.resourceGroupMapper = resourceGroupMapper;
    }

    private ResourceGroupMapper resourceGroupMapper;

    @Override
    public ResourceGroupMapper getMapper() {
        return resourceGroupMapper;
    }

    public ResourceGroup getGroup(String user, String groupName) {
        ResourceGroup group = resourceGroupMapper.getByName(user, groupName);
        if (group == null) {
            throw new RestNotFoundException("资源分组未配置: " + groupName);
        }
        return group;
    }

    public RestError deleteByUser(String user, Integer groupId) {
        ResourceGroup origin = resourceGroupMapper.get(groupId);
        if (origin == null) {
            return RestEnum.RecordNotFound.value();
        }
        origin.setValid(false);
        resourceGroupMapper.update(origin);
        return RestError.create(0, "资源组删除成功: " + groupId);
    }

    public ResourceGroup updateLabel(Integer groupId, String label) {
        ResourceGroup origin = resourceGroupMapper.get(groupId);
        if (origin == null) {
            return null;
        }
        origin.setLabel(label);
        origin.setUpdateTime(new Date());
        resourceGroupMapper.update(origin);
        return origin;
    }

}