package win.doyto.i18n.module.locale;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;
import win.doyto.query.mybatis.IMapper;

@Mapper
//@CacheNamespace(implementation = org.mybatis.caches.hazelcast.HazelcastCache.class)
public interface ResourceLocaleMapper extends IMapper<ResourceLocale, Integer, ResourceLocaleQuery> {
    String Table = "i18n_resource_locale";

    /*-------------------start--------------------------*/
    /*____________________end___________________________*/

    @Select(LIST_ + Table + _WHERE_ID)
    ResourceLocale get(Integer id);

    @Delete(DELETE_ + Table + _WHERE_ID)
    int delete(Integer id);

    @Insert({
            "insert into",
            Table,
            "(`groupId`,`locale`,`language`,`baiduTranLang`,`status`)",
            "values",
            "(#{groupId},#{locale},#{language},#{baiduTranLang},1)"
    })
    @Options(useGeneratedKeys = true)
    Integer insert(ResourceLocale record);

    @UpdateProvider(type = ResourceLocaleSqlProvider.class, method = "update")
    int update(ResourceLocale record);

    class ResourceLocaleSqlProvider {

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