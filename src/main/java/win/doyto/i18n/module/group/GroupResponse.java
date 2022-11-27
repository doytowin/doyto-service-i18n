package win.doyto.i18n.module.group;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * GroupResponse
 *
 * @author f0rb on 2019-05-23
 */
@Getter
@Setter
public class GroupResponse {

    private Integer id;

    @JsonAlias("createUserId")
    private String owner;

    private String name;

    private String label;

    private LocalDateTime createTime;

}
