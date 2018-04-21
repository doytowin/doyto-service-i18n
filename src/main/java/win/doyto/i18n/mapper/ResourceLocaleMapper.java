package win.doyto.i18n.mapper;

import java.io.Serializable;
import java.util.List;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;
import win.doyto.i18n.model.ResourceLocale;
import win.doyto.web.service.IMapper;

@Mapper
//@CacheNamespace(implementation = org.mybatis.caches.hazelcast.HazelcastCache.class)
public interface ResourceLocaleMapper extends IMapper<ResourceLocale, ResourceLocale> {
    String Table = "i18n_resource_locale";

    /*-------------------start--------------------------*/
    /*____________________end___________________________*/

    @Select(LIST_ + Table + _WHERE_ID)
    ResourceLocale get(Serializable id);

    @Delete(DELETE_ + Table + _WHERE_ID)
    int delete(Serializable id);

    @Insert({
            "insert into",
            Table,
            "(`groupId`,`locale`,`language`,`baiduTranLang`,`status`)",
            "values",
            "(#{groupId},#{locale},#{language},#{baiduTranLang},1)"
    })
    @Options(useGeneratedKeys = true)
    int insert(ResourceLocale record);

    @UpdateProvider(type = ResourceLocaleSqlProvider.class, method = "update")
    int update(ResourceLocale record);

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

    @SelectProvider(type = ResourceLocaleSqlProvider.class, method = "query")
    List<ResourceLocale> query(ResourceLocale record);

    @SelectProvider(type = ResourceLocaleSqlProvider.class, method = "count")
    long count(ResourceLocale record);

    class ResourceLocaleSqlProvider {
        private String queryOrCount(final ResourceLocale record, final boolean query) {
            return new SQL() {{
                SELECT(query ? "*" : "COUNT(*)");
                FROM(Table);
                //if (record.getName() != null) {
                //    WHERE("name like CONCAT(#{name},'%')");
                //}
                if (record.getGroupId() != null) {
                    WHERE("groupId = #{groupId}");
                }
                if (record.getLocale() != null) {
                    WHERE("locale = #{locale}");
                }
                if (record.getLanguage() != null) {
                    WHERE("language = #{language}");
                }
            }}.toString();// + (query && record.needPaging() ? _LIMIT_OFFSET : "");
        }

        public String query(ResourceLocale record) {
            return queryOrCount(record, true);
        }

        public String count(ResourceLocale record) {
            return queryOrCount(record, false);
        }

        public String update(final ResourceLocale record) {
            return new SQL() {{
                UPDATE(Table);
                if (record.getLocale() != null) {
                    SET("`locale` = #{locale,jdbcType=VARCHAR}");
                }
                if (record.getLanguage() != null) {
                    SET("`language` = #{language,jdbcType=VARCHAR}");
                }
                if (record.getBaiduTranLang() != null) {
                    SET("`baiduTranLang` = #{baiduTranLang,jdbcType=VARCHAR}");
                }
                if (record.getUpdateTime() != null) {
                    SET("`updateTime` = #{updateTime,jdbcType=TIMESTAMP}");
                }
                if (record.getStatus() != null) {
                    SET("`status` = #{status,jdbcType=BIT}");
                }
                WHERE("id = #{id,jdbcType=INTEGER}");
            }}.toString();
        }
    }
}