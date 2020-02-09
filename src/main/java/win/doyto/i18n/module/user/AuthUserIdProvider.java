package win.doyto.i18n.module.user;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import win.doyto.auth.security.AuthUser;
import win.doyto.query.entity.UserIdProvider;

/**
 * AuthUserIdProvider
 *
 * @author f0rb on 2020-02-09
 */
@Component
public class AuthUserIdProvider implements UserIdProvider<Integer> {

    @Override
    public Integer getUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            if (authentication.getPrincipal() instanceof AuthUser) {
                return ((AuthUser) authentication.getPrincipal()).getId().intValue();
            } else if (authentication.getPrincipal() instanceof User) {
                return 1;
            }
        }
        return null;
    }
}
