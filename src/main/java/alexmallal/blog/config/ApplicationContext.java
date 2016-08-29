package alexmallal.blog.config;

import java.text.SimpleDateFormat;
import java.util.List;


import org.springframework.context.MessageSource;
import org.springframework.context.annotation.*;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.orm.jpa.support.PersistenceAnnotationBeanPostProcessor;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;
import org.springframework.web.servlet.view.tiles3.TilesView;
import org.springframework.web.servlet.view.tiles3.TilesViewResolver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;


@Configuration
@EnableWebMvc
//component scan for other configs
@ComponentScan(basePackages="alexmallal.blog.core", useDefaultFilters=false, includeFilters={@Filter(type = FilterType.REGEX, pattern="alexmallal.blog.core.web.*")})
public class ApplicationContext extends WebMvcConfigurerAdapter {

    private static final String MESSAGE_SOURCE_BASE_NAME = "i18n/messages";

//    private static final String VIEW_RESOLVER_PREFIX = "/WEB-INF/jsp/";
//    private static final String VIEW_RESOLVER_SUFFIX = ".jsp";

    //allows js and css to be served by spring mvc
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
    }

    //low order handle static resources, handle from /
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    //injecting messages (dynamic text into views), used for custom validation messages
    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();

        messageSource.setBasename(MESSAGE_SOURCE_BASE_NAME);
        //  Set whether to use the message code as default message instead of throwing a NoSuchMessageException
        messageSource.setUseCodeAsDefaultMessage(true);

        return messageSource;
    }

//    //where to find views to resolve
//    @Bean
//    public ViewResolver viewResolver() {
//        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
//
//        viewResolver.setViewClass(JstlView.class);
//        viewResolver.setPrefix(VIEW_RESOLVER_PREFIX);
//        viewResolver.setSuffix(VIEW_RESOLVER_SUFFIX);
//
//        return viewResolver;
//    }
    @Bean
    public PersistenceAnnotationBeanPostProcessor persistenceAnnotationBeanPostProcessor() {
            // enable injection of EntityManager to beans with @PersistenceContext annotation
            return new PersistenceAnnotationBeanPostProcessor();
    }
    
    @Override
    @Order(1)
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
      configurer.favorPathExtension(false).
              favorParameter(true).
              parameterName("mediaType").
              ignoreAcceptHeader(true).
              useJaf(false).
              defaultContentType(MediaType.APPLICATION_JSON).
              mediaType("xml", MediaType.APPLICATION_XML).
              mediaType("json", MediaType.APPLICATION_JSON);
    }
    
//    @Bean
//    public MappingJackson2HttpMessageConverter jsonConverter() {
//        MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
//        ObjectMapper objectMapper = new ObjectMapper();
//        jsonConverter.setObjectMapper(objectMapper);
//        return jsonConverter;
//    }
    
    @Override
    @Order(2)
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(converter());
    }
    
    @Bean
    public MappingJackson2HttpMessageConverter converter() {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        ObjectMapper objectMapper = new ObjectMapper();
        Hibernate4Module hm = new Hibernate4Module();
        hm.enable(Hibernate4Module.Feature.FORCE_LAZY_LOADING);
        objectMapper.registerModule(hm);
        objectMapper.setDateFormat(new SimpleDateFormat());
        converter.setObjectMapper(objectMapper);
        return converter;
    }


    @Order(3)
    @Bean
      public ViewResolver getViewResolver() {
    
        TilesViewResolver tilesViewResolver = new TilesViewResolver();
    
        tilesViewResolver.setViewClass(TilesView.class);
    
        return tilesViewResolver;
      }
   
      @Bean
      public TilesConfigurer getTilesConfigurer() {
    
        TilesConfigurer tilesConfigurer = new TilesConfigurer();
    
        tilesConfigurer.setDefinitionsFactoryClass(TilesDefinitionConfig.class);
       tilesConfigurer.setCheckRefresh(true);
    
        TilesDefinitionConfig.addDefinitions();
    
        return tilesConfigurer;
      }
}
