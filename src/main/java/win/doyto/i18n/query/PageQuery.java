package win.doyto.i18n.query;

import javax.validation.constraints.Pattern;

import lombok.Setter;
import win.doyto.web.Pageable;

/**
 * PageQuery
 *
 * @author f0rb on 2018-04-24.
 */
//@Getter
public class PageQuery implements Pageable {

    @Pattern(regexp = "\\d+")
    private String pn; //从前台传入的用户查看的页号，结合limit确定offset

    @Pattern(regexp = "0*[1-9]\\d*")
    @Setter
    private String size = "10"; //分页大小

    @Setter
    private String sort; //分页倒序

    @Override
    public boolean needPaging() {
        return pn != null;
    }

    @Override
    public Long getOffset() {
        return (long) getPage() * getLimit();
    }

    @Override
    public Integer getLimit() {
        return Integer.valueOf(size);
    }

    @Override
    public void setLimit(int limit) {
        setSize(String.valueOf(limit > 0 ? limit : 10));
    }

    @Override
    public Integer getPage() {
        return Integer.valueOf(pn);
    }

    @Override
    public void setPage(int page) {
        pn = String.valueOf(page >=0 ? page : 0);
    }
}
