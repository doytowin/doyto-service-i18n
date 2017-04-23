package win.doyto.i18n.configure;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * WebMvcConfigurer
 *
 * @author f0rb on 2017-04-23.
 */
@Configuration
public class WebMvcConfigure extends WebMvcConfigurerAdapter {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        //registry.addMapping("/openapi/**").allowedOrigins("*");
        //registry.addMapping("/api/**").allowedOrigins("http://localhost:9017");
        //registry.addMapping("/api/i18n/i18n/zh_CN").allowedOrigins("http://localhost:8080");
    }

    ///*
    // * Configure ContentNegotiationManager
    // */
    //@Override
    //public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
    //    configurer.ignoreAcceptHeader(true).defaultContentType(MediaType.TEXT_HTML);
    //}
    //
    ///*
    // * Configure ContentNegotiatingViewResolver
    // */
    //@Bean
    //public ViewResolver contentNegotiatingViewResolver(ContentNegotiationManager manager) {
    //    ContentNegotiatingViewResolver resolver = new ContentNegotiatingViewResolver();
    //    resolver.setContentNegotiationManager(manager);
    //
    //    // Define all possible view resolvers
    //    List<ViewResolver> resolvers = new ArrayList<ViewResolver>();
    //
    //    resolvers.add(jsonViewResolver());
    //    resolvers.add(excelViewResolver());
    //
    //    resolver.setViewResolvers(resolvers);
    //    return resolver;
    //}
    //
    ///*
    // * Configure View resolver to provide JSON output using JACKSON library to
    // * convert object in JSON format.
    // */
    //@Bean
    //public ViewResolver jsonViewResolver() {
    //    return (viewName, locale) -> new FastJsonJsonView();
    //}
    ///*
    // * Configure View resolver to provide XLSX output using Apache POI library to
    // * generate XLS output for an object content
    // */
    //@Bean
    //public ViewResolver excelViewResolver() {
    //    return (viewName, locale) -> {
    //        if (viewName.equals("i18n_view")) {
    //            return new I18nXlsxView();
    //        }
    //        return null;
    //    };
    //}
}
