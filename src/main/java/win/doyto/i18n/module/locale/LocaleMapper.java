package win.doyto.i18n.module.locale;

import org.apache.ibatis.annotations.Mapper;
import win.doyto.query.mybatis.CrudMapper;
import win.doyto.query.mybatis.MapperTable;

@Mapper
@MapperTable(LocaleEntity.TABLE)
interface LocaleMapper extends CrudMapper<LocaleEntity, Integer, LocaleQuery> {

}