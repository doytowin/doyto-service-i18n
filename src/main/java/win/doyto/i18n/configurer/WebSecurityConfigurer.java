//package win.doyto.i18n.configurer;
//
//import javax.annotation.Resource;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import win.doyto.i18n.service.UserSecurityService;
//
///**
// * WebSecurityConfigure
// *
// * @author f0rb on 2017-04-29.
// */
//@Configuration
//@EnableWebSecurity
//public class WebSecurityConfigurer extends WebSecurityConfigurerAdapter {
//
//    @Resource
//    DoytoProperties doytoProperties;
//
//    @Resource
//    UserSecurityService userSecurityService;
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests()
//            .antMatchers("/", "/static/", "/static/**").permitAll()
//            .anyRequest().authenticated()
//            .and()
//            .formLogin().loginPage(doytoProperties.getLoginUrl())
//            .and()
//            .rememberMe()
//            .key(doytoProperties.getRememberMeToken())
//            .rememberMeCookieName(doytoProperties.getRememberMeCookie())
//            .userDetailsService(userSecurityService)
//            .and()
//            .csrf().disable()
//        ;
//    }
//
//}
