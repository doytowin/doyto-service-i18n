package win.doyto.i18n.configurer;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * WebMvcConfigurer
 *
 * @author f0rb on 2017-04-23.
 */
@Configuration
public class WebMvcConfigurer extends WebMvcConfigurerAdapter {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/openapi/**").allowedOrigins("*");
        //registry.addMapping("/api/**").allowedOrigins("http://localhost:9017");
        //registry.addMapping("/api/i18n/i18n/zh_CN").allowedOrigins("http://localhost:8080");
    }

    @Resource
    public void setMappingJackson2HttpMessageConverter(MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter) {
        List<MediaType> supportedMediaTypes=new ArrayList<>();
        supportedMediaTypes.add(MediaType.APPLICATION_JSON);
        supportedMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
        supportedMediaTypes.add(MediaType.valueOf("application/*+json"));
        supportedMediaTypes.add(MediaType.valueOf("application/*+json;charset=UTF-8"));
        mappingJackson2HttpMessageConverter.setSupportedMediaTypes(supportedMediaTypes);

        ObjectMapper objectMapper = mappingJackson2HttpMessageConverter.getObjectMapper();
        objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        objectMapper.configure(JsonParser.Feature.IGNORE_UNDEFINED, true);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        //objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
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
