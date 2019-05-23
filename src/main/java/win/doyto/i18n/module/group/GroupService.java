package win.doyto.i18n.module.group;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import win.doyto.common.web.ErrorCode;
import win.doyto.i18n.common.I18nErrorCode;
import win.doyto.query.mybatis.AbstractMyBatisService;

import java.util.Date;

/**
 * GroupDefaultService
 *
 * @author f0rb on 2017-03-29.
 */
@Slf4j
@Service
class GroupService extends AbstractMyBatisService<GroupEntity, Integer, GroupQuery> {

    public GroupService(GroupMapper groupMapper) {
        super(groupMapper);
    }

    public GroupEntity getByName(String owner, String name) {
        return query(GroupQuery.builder().owner(owner).nameLike(name).build()).get(0);
    }

    public void updateLabel(Integer groupId, String label) {
        GroupEntity origin = get(groupId);
        ErrorCode.assertNotNull(origin, I18nErrorCode.RECORD_NOT_FOUND);
        origin.setLabel(label);
        origin.setUpdateTime(new Date());
        update(origin);
    }

}