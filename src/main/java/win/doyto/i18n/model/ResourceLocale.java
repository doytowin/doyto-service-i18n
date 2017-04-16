package win.doyto.i18n.model;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import win.doyto.web.PageableModel;

/**
 * ResourceLocale
 *
 * @author f0rb on 2017-04-16.
 */
@Getter
@Setter
public class ResourceLocale extends PageableModel<ResourceLocale> {
    private static final long serialVersionUID = 1L;

    private Integer id;

    private Integer groupId;

    private String locale;

    private Date createTime;

    private Date updateTime;

    private Boolean statu;

}