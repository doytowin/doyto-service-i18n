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

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        AuthenticationSuccessHandler successHandler = (request, response, auth)
                -> writeJson(response, PresetErrorCode.SUCCESS);
        AuthenticationFailureHandler failureHandler = (request, response, auth)
                -> writeJson(response, ErrorCode.build("登录失败"));
        AuthenticationEntryPoint authenticationEntryPoint = (request, response, authException)
                -> writeJson(response, ErrorCode.build(101, "登录过期"));

        String[] patterns = {"/", "/login", "/openapi/**", "/actuator/**", "/js/**", "/css/**"};
        http.authorizeRequests().antMatchers(patterns).permitAll().and()
            .authorizeRequests().anyRequest().authenticated().and()
            .formLogin().successHandler(successHandler).failureHandler(failureHandler).and()
            .rememberMe().and()
            .logout().logoutSuccessHandler((request, response, auth) -> writeJson(response, PresetErrorCode.SUCCESS)).and()
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
            ErrorCode.assertFalse(user instanceof AnonymousAuthenticationToken, ErrorCode.build("登录过期"));
            return user.getPrincipal();
        }
    }

}
