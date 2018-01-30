package win.doyto.i18n.component.mybatis;

import java.util.Map;
import java.util.Properties;
import java.util.regex.Pattern;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import win.doyto.web.Pageable;

@Slf4j
@Intercepts({@Signature(
        type = Executor.class,
        method = "query",
        args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}
)})
public class MysqlPaginationInterceptor implements Interceptor {

    private final static Pattern SQL_SELECT_REGEX = Pattern.compile("(?is)^\\s*SELECT.*$");
    private final static Pattern SQL_COUNT_REGEX = Pattern.compile("(?is)^\\s*SELECT\\s+COUNT\\s*\\(\\s*(?:\\*|\\w+)\\s*\\).*$");
    //private final static String SQL_SELECT_REGEX = "(?is)^\\s*SELECT.*$";
    //private final static String SQL_COUNT_REGEX = "(?is)^\\s*SELECT\\s+COUNT\\s*\\(\\s*(?:\\*|\\w+)\\s*\\).*$";

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Executor target = (Executor) invocation.getTarget();
        Object[] args = invocation.getArgs();
        MappedStatement ms = (MappedStatement) args[0];
        BoundSql boundSql = ms.getBoundSql(args[1]);
        String sql = boundSql.getSql();

        // 只有为select查询语句时才进行下一步
        if (StringUtils.isNotBlank(sql) && SQL_SELECT_REGEX.matcher(sql).matches() && !SQL_COUNT_REGEX.matcher(sql).matches()) {
            Executor executor = (Executor) FieldUtils.readField(target, "delegate", true);
            Object parameterObject = args[1];
            Pageable pageable = null;
            if (parameterObject instanceof Pageable) {
                pageable = (Pageable) parameterObject;
            } else if (parameterObject instanceof Map) {
                Map paramMap = (Map) parameterObject;
                Object param1 = paramMap.get("param1");
                if (param1 instanceof Pageable) {
                    pageable = (Pageable) param1;
                }
            }
            if (pageable != null && pageable.needPaging()) {
                writePageSql(boundSql, pageable.getLimit(), pageable.getOffset());
                return invocation.proceed();
            }
            // 反射获取 RowBounds 对象。
            RowBounds rowBounds = (RowBounds) args[2];
            // 分页参数存在且不为默认值时进行分页SQL构造
            if (rowBounds != null && rowBounds != RowBounds.DEFAULT) {
                writePageSql(boundSql, rowBounds.getLimit(), rowBounds.getOffset());
                // 一定要还原否则将无法得到下一组数据(第一次的数据被缓存了)
                FieldUtils.writeField(rowBounds, "offset", RowBounds.NO_ROW_OFFSET, true);
                FieldUtils.writeField(rowBounds, "limit", RowBounds.NO_ROW_LIMIT, true);
            }
        }
        return invocation.proceed();
    }

    public void writePageSql(BoundSql boundSql, int limit, long offset) throws IllegalAccessException {
        String sql = boundSql.getSql();
        log.info("old sql:\n{}", sql);
        String newSql =  sql + " LIMIT " + limit + " OFFSET " + offset;
        log.info("new sql: {}", newSql);
        FieldUtils.writeField(boundSql, "sql", sql, true);
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties arg0) {
        log.info(arg0.toString());
    }
}