package web.config;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import web.models.User;

import javax.annotation.Resource;
import java.util.Properties;

@Configuration
@PropertySource("classpath:db.properties")
@ComponentScan("web")
@EnableWebMvc
public class AppConfig implements WebMvcConfigurer {
    private final ApplicationContext applicationContext;

    @Resource
    private Environment env;

    @Autowired
    public AppConfig(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Bean
    public SessionFactory getSessionFactory() {
        Properties properties = new Properties();
        properties.put(org.hibernate.cfg.Environment.URL, env.getProperty("db.url"));
        properties.put(org.hibernate.cfg.Environment.USER, env.getProperty("db.username"));
        properties.put(org.hibernate.cfg.Environment.PASS, env.getProperty("db.password"));
        properties.put(org.hibernate.cfg.Environment.SHOW_SQL, env.getProperty("hibernate.show_sql"));

        org.hibernate.cfg.Configuration configuration = new org.hibernate.cfg.Configuration();
        configuration.setProperties(properties);

        configuration.addAnnotatedClass(User.class);

        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties()).build();

        return configuration.buildSessionFactory(serviceRegistry);
    }

    @Bean
    public SpringResourceTemplateResolver templateResolver() {
        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
        templateResolver.setApplicationContext(applicationContext);
        templateResolver.setPrefix("/WEB-INF/pages/");
        templateResolver.setSuffix(".html");
        return templateResolver;
    }

    @Bean
    public SpringTemplateEngine templateEngine() {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver());
        templateEngine.setEnableSpringELCompiler(true);
        return templateEngine;
    }

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        ThymeleafViewResolver resolver = new ThymeleafViewResolver();
        resolver.setTemplateEngine(templateEngine());
        resolver.setCharacterEncoding("UTF-8");
        registry.viewResolver(resolver);
    }
}
