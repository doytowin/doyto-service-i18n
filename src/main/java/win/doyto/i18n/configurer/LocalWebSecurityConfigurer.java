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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import win.doyto.common.web.ErrorCode;
import win.doyto.common.web.HttpUtil;
import win.doyto.common.web.JsonBody;
import win.doyto.i18n.common.I18nErrorCode;

/**
 * LocalWebSecurityConfigurer
 *
 * @author f0rb
 * @date 2019-05-22
 */
@Profile("local")
@Configuration
public class LocalWebSecurityConfigurer extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        String[] patterns = {"/", "/login", "/openapi/**", "/actuator/**", "/js/**", "/css/**"};
        AuthenticationSuccessHandler successHandler = (request, response, auth)
            -> HttpUtil.writeJson(response, ErrorCode.SUCCESS);
        AuthenticationFailureHandler failureHandler = (request, response, auth)
            -> HttpUtil.writeJson(response, ErrorCode.build(2, "登录失败"));
        AuthenticationEntryPoint authenticationEntryPoint = (request, response, authException)
            -> HttpUtil.writeJson(response, ErrorCode.build(1, "登录过期"));

        http.authorizeRequests().antMatchers(patterns).permitAll().and()
            .authorizeRequests().anyRequest().authenticated().and()
            .formLogin().successHandler(successHandler).failureHandler(failureHandler).and()
            .rememberMe().and()
            .logout().and()
            .exceptionHandling().authenticationEntryPoint(authenticationEntryPoint).and()
            .csrf().disable()
            .httpBasic().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);
    }

    @JsonBody
    @RestController
    static class AuthController {
        @RequestMapping("/api/user")
        public Object user(Authentication user) {
            ErrorCode.assertFalse(user instanceof AnonymousAuthenticationToken, I18nErrorCode.LOGIN_EXPIRED);
            return user.getPrincipal();
        }
    }
}
