package win.doyto.i18n.module.group;

import win.doyto.query.mybatis.AbstractMockMapper;

/**
 * MockGroupMapper
 *
 * @author f0rb on 2018-08-22.
 */
public class MockGroupMapper extends AbstractMockMapper<GroupEntity, Integer, GroupQuery> implements GroupMapper {

    public MockGroupMapper() {
        super(GroupEntity.TABLE);
    }

}
