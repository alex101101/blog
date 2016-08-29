package alexmallal.blog.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan(basePackages={"alexmallal.elasticsearch", "alexmallal.blog.core"},
excludeFilters=@Filter(type = FilterType.REGEX, pattern="alexmallal.blog.core.web.*"))
//implement persistence configs
@Import({PersistenceContext.class, SecurityConfig.class, RedisConfig.class, RabbitMqConfig.class})
public class RootConfig {

}
