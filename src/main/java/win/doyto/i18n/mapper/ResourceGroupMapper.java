package win.doyto.i18n.mapper;

import java.io.Serializable;
import java.util.List;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;
import win.doyto.i18n.model.ResourceGroup;
import win.doyto.web.service.IMapper;

@Mapper
//@CacheNamespace(implementation = org.mybatis.caches.hazelcast.HazelcastCache.class)
public interface ResourceGroupMapper extends IMapper<ResourceGroup> {
    String Table = "i18n_group";

    /*-------------------start--------------------------*/

    @Select(LIST_ + Table + " WHERE name = #{name}")
    ResourceGroup getByName(@Param("name") String name);

    /*____________________end___________________________*/

    @Select(LIST_ + Table + _WHERE_ID)
    ResourceGroup get(Serializable id);

    @Delete(DELETE_ + Table + _WHERE_ID)
    int delete(Serializable id);

    @Insert({
            "insert into",
            Table,
            "(`label`,`value`,`memo`)",
            "values",
            "(#{label},#{value},#{memo})"
    })
    @Options(useGeneratedKeys = true)
    int insert(ResourceGroup record);

    @UpdateProvider(type = GroupSqlProvider.class, method = "update")
    int update(ResourceGroup record);

    /**
     * 检查某列是否存在某值
     *
     * @param column 列名
     * @param value  待检值
     * @return 如果值存在, 则返回true; 否则返回false
     */
    @Select(HAS_ + Table + " WHERE ${column} = #{value}")
    @Options(useCache = false)
    Boolean hasValueOnColumn(@Param("column") String column, @Param("value") String value);

    @SelectProvider(type = GroupSqlProvider.class, method = "query")
    List<ResourceGroup> query(ResourceGroup record);

    @SelectProvider(type = GroupSqlProvider.class, method = "count")
    long count(ResourceGroup record);

    class GroupSqlProvider {
        private String queryOrCount(final ResourceGroup record, final boolean query) {
            return new SQL() {{
                SELECT(query ? "*" : "COUNT(*)");
                FROM(Table);
                if (record.getName() != null) {
                    WHERE("name like CONCAT(#{name},'%')");
                }
            }}.toString();
        }

        public String query(ResourceGroup record) {
            return queryOrCount(record, true);
        }

        public String count(ResourceGroup record) {
            return queryOrCount(record, false);
        }

        public String update(final ResourceGroup record) {
            return new SQL() {{
                UPDATE(Table);
                if (record.getName() != null) {
                    SET("`name` = #{name,jdbcType=VARCHAR}");
                }
                /*if (record.getUpdateTime() != null) {
                    SET("`updateTime` = #{updateTime,jdbcType=TIMESTAMP}");
                }*/
                WHERE("id = #{id,jdbcType=INTEGER}");
            }}.toString();
        }
    }
}