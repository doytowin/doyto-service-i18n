package win.doyto.i18n.module.group;

import org.apache.ibatis.annotations.Mapper;
import win.doyto.query.mybatis.CrudMapper;
import win.doyto.query.mybatis.MapperTable;

@Mapper
@MapperTable(GroupEntity.TABLE)
interface GroupMapper extends CrudMapper<GroupEntity, Integer, GroupQuery> {

}