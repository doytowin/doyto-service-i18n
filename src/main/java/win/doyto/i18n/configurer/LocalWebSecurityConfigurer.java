package win.doyto.i18n.configurer;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import win.doyto.query.web.response.ErrorCode;
import win.doyto.query.web.response.JsonBody;
import win.doyto.query.web.response.PresetErrorCode;

import static win.doyto.query.web.util.HttpUtil.writeJson;


/**
 * LocalWebSecurityConfigurer
 *
 * @author f0rb
 * @date 2019-05-22
 */
@Profile({"local", "test", "demo"})
@Configuration
@SuppressWarnings({"java:S4834", "java:S4502"})
public class LocalWebSecurityConfigurer extends WebSecurityConfigurerAdapter {
    private static final ErrorCode LOGIN_EXPIRED = ErrorCode.build(1001, "登录过期");

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        AuthenticationSuccessHandler successHandler = (request, response, auth)
                -> writeJson(response, PresetErrorCode.SUCCESS);
        LogoutSuccessHandler logoutSuccessHandler = (request, response, auth)
                -> writeJson(response, PresetErrorCode.SUCCESS);
        AuthenticationFailureHandler failureHandler = (request, response, auth)
                -> writeJson(response, ErrorCode.build("登录失败"));
        AuthenticationEntryPoint authenticationEntryPoint = (request, response, authException)
                -> writeJson(response, LOGIN_EXPIRED);

        String[] patterns = {"/", "/login", "/openapi/**", "/actuator/**", "/js/**", "/css/**"};
        http.authorizeRequests().antMatchers(patterns).permitAll().and()
            .authorizeRequests().anyRequest().authenticated().and()
            .formLogin().successHandler(successHandler).failureHandler(failureHandler).and()
            .logout().logoutSuccessHandler(logoutSuccessHandler).and()
            .exceptionHandling().authenticationEntryPoint(authenticationEntryPoint).and()
            .csrf().disable()
            .httpBasic().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);
    }

    @JsonBody
    @RestController
    static class AuthController {
        @GetMapping("/session/user")
        public Object user(Authentication user) {
            ErrorCode.assertFalse(user instanceof AnonymousAuthenticationToken, LOGIN_EXPIRED);
            return user.getPrincipal();
        }
    }

}
