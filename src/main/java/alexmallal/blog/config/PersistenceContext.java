package alexmallal.blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.support.AbstractPlatformTransactionManager;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManagerFactory;
import javax.persistence.spi.PersistenceProvider;
import javax.sql.DataSource;
import java.util.Properties;


@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "alexmallal.blog.core")
@PropertySource("classpath:props.properties")
public class PersistenceContext {
	
	//change hibernate prooperties using environment
	@Autowired
	private Environment environment;
    
    //transaction management
    @Bean
    public AbstractPlatformTransactionManager transactionManager() {
        return new JpaTransactionManager(entityManagerFactory());
    }
    
    //configure hibernate as manager
    @Bean
    public EntityManagerFactory entityManagerFactory() {
        final LocalContainerEntityManagerFactoryBean bean = new LocalContainerEntityManagerFactoryBean();
        bean.setDataSource(dataSource());
        bean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        bean.setPackagesToScan("alexmallal.blog.core");
        
        final Properties props = new Properties();
        props.setProperty("hibernate.dialect", environment.getRequiredProperty("hibernate.dialect"));
        props.setProperty("hibernate.hbm2ddl.auto", environment.getRequiredProperty("hibernate.hbm2ddl.auto"));
        props.setProperty("hibernate.show_sql", environment.getRequiredProperty("hibernate.show_sql"));
        props.setProperty("hibernate.format_sql", environment.getRequiredProperty("hibernate.format_sql"));

        bean.setJpaProperties(props);
        bean.setPersistenceUnitName( "mytestdomain" );
        //bean.setPersistenceProviderClass(PersistenceProvider.class);
        bean.afterPropertiesSet();
        
        return bean.getObject();
    }
    
    //find and configure JNDI datasource (mysql)
    @Bean
    public DataSource dataSource() {
        final JndiDataSourceLookup dsLookup = new JndiDataSourceLookup();
        dsLookup.setResourceRef(true);
        DataSource dataSource = dsLookup.getDataSource("jdbc/cms");
        return dataSource;
    }
}
