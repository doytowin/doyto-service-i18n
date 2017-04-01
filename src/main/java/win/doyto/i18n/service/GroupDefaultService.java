package win.doyto.i18n.service;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import win.doyto.i18n.mapper.GroupMapper;
import win.doyto.i18n.model.Group;
import win.doyto.web.service.AbstractService;

/**
 * GroupDefaultService
 *
 * @author f0rb on 2017-03-29.
 */
@Slf4j
@Service
public class GroupDefaultService extends AbstractService<Group> implements GroupService {
    @Resource
    private GroupMapper groupMapper;

    @Override
    public GroupMapper getIMapper() {
        return groupMapper;
    }

    @Override
    public Group save(Group group) {
        Group origin = groupMapper.get(group.getId());
        if (origin == null) {
            return null;
        }
        //origin.setLabel(group.getLabel());
        //origin.setValue(group.getValue());

        //origin.setUpdateUserId(AppContext.getLoginUserId());
        //origin.setUpdateTime(new Date());
        groupMapper.update(origin);
        return origin;
    }

}