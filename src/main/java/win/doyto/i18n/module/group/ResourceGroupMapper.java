package win.doyto.i18n.module.group;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;
import win.doyto.query.mybatis.IMapper;

@Mapper
public interface ResourceGroupMapper extends IMapper<ResourceGroup, Integer, ResourceGroupQuery> {
    String Table = ResourceGroup.TABLE;

    /*-------------------start--------------------------*/

    @Select(LIST_ + Table + " WHERE name = #{name} and owner = #{owner}")
    ResourceGroup getByName(@Param("owner") String owner, @Param("name") String name);

    /*____________________end___________________________*/

    @Select(LIST_ + Table + _WHERE_ID)
    ResourceGroup get(Integer id);

    @Delete(DELETE_ + Table + _WHERE_ID)
    int delete(Integer id);

    //@Insert({
    //        "insert into",
    //        Table,
    //        "(`label`,`value`,`memo`)",
    //        "values",
    //        "(#{label},#{value},#{memo})"
    //})
    //@Options(useGeneratedKeys = true)
    //int insert(ResourceGroup record);

    @UpdateProvider(type = ResourceGroupQueryBuilder.class, method = "update")
    int update(ResourceGroup record);

    class ResourceGroupQueryBuilder {
        public String update(final ResourceGroup record) {
            return new SQL() {{
                UPDATE(Table);
                if (record.getLabel() != null) {
                    SET("`label` = #{label,jdbcType=VARCHAR}");
                }
                if (record.getDeleted() != null) {
                    SET("`deleted` = #{deleted,jdbcType=BOOLEAN}");
                }
                WHERE("id = #{id,jdbcType=INTEGER}");
            }}.toString();
        }
    }
}