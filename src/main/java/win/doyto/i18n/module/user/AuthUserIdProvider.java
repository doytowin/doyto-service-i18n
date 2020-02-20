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
public class AuthUserIdProvider implements UserIdProvider<String> {

    @Override
    public String getUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof AuthUser) {
                return ((AuthUser) principal).getUsername();
            } else if (principal instanceof User) {
                return ((User) principal).getUsername();
            }
        }
        return null;
    }
}
