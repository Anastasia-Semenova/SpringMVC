package ru.itis.javalab.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import freemarker.template.TemplateExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassRelativeResourceLoader;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.session.jdbc.config.annotation.web.http.EnableJdbcHttpSession;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.ui.freemarker.SpringTemplateLoader;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@EnableWebMvc
@Configuration
@EnableJdbcHttpSession
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "ru.itis.javalab.repositories")
@PropertySource("classpath:application.properties")
@ComponentScan(basePackages = "ru.itis.javalab")
public class ApplicationConfig {

    private final Environment environment;

    public ApplicationConfig(Environment environment) {
        this.environment = environment;
    }

    @Bean
    public HikariConfig hikariConfig() {
        HikariConfig hikariConfig = new HikariConfig();

        hikariConfig.setJdbcUrl(environment.getProperty("db.url"));
        hikariConfig.setMaximumPoolSize(Integer.parseInt(environment.getProperty("db.hikari.max-pool-size")));
        hikariConfig.setUsername(environment.getProperty("db.username"));
        hikariConfig.setPassword(environment.getProperty("db.password"));
        hikariConfig.setDriverClassName(environment.getProperty("db.driver.classname"));

        hikariConfig.addDataSourceProperty(
                "characterEncoding",
                environment.getProperty("datasource.encoding.character"));
        hikariConfig.addDataSourceProperty(
                "useUnicode",
                environment.getProperty("datasource.encoding.use-unicode"));

        return hikariConfig;
    }

    @Bean
    public DataSource dataSource() {
        return new HikariDataSource(hikariConfig());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setDefaultEncoding("UTF-8");
        mailSender.setHost(environment.getProperty("mail.host"));
        mailSender.setPort(Integer.parseInt(environment.getProperty("mail.port")));
        mailSender.setUsername(environment.getProperty("mail.username"));
        mailSender.setPassword(environment.getProperty("mail.password"));

        Properties mailProperties = mailSender.getJavaMailProperties();
        mailProperties.put("mail.smtp.starttls.enable", environment.getProperty("mail.smtp.starttls.enable"));
        mailProperties.put("mail.smtp.allow8bitmime", environment.getProperty("mail.smtp.allow8bitmime"));
        mailProperties.put("mail.smtp.ssl.trust", environment.getProperty("mail.smtp.ssl.trust"));
        mailProperties.put("mail.debug", environment.getProperty("mail.debug"));

        return mailSender;
    }

    @Bean
    public ExecutorService executorService() {
        return Executors.newCachedThreadPool();
    }

    @Bean
    public freemarker.template.Configuration configuration() {
        freemarker.template.Configuration configuration =
                new freemarker.template.Configuration(freemarker.template.Configuration.VERSION_2_3_30);

        configuration.setDefaultEncoding("UTF-8");
        configuration.setTemplateLoader(
                new SpringTemplateLoader(new ClassRelativeResourceLoader(this.getClass()),
                        "/"));
        configuration.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);

        return configuration;
    }

    @Bean
    public FreeMarkerViewResolver freemarkerViewResolver() {
        FreeMarkerViewResolver resolver = new FreeMarkerViewResolver();
        resolver.setPrefix("");
        resolver.setSuffix(".ftlh");
        resolver.setContentType("text/html;charset=UTF-8");
        return resolver;
    }

    @Bean
    public FreeMarkerConfigurer freemarkerConfig() {
        FreeMarkerConfigurer configurer = new FreeMarkerConfigurer();
        configurer.setTemplateLoaderPath("classpath:/templates/");
        return configurer;
    }

    @Bean
    public NamedParameterJdbcTemplate jdbcTemplate() {
        return new NamedParameterJdbcTemplate(dataSource());
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
        hibernateJpaVendorAdapter.setDatabase(Database.POSTGRESQL);

        LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactory.setDataSource(dataSource());
        entityManagerFactory.setPackagesToScan("ru.itis.javalab");
        entityManagerFactory.setJpaVendorAdapter(hibernateJpaVendorAdapter);
        entityManagerFactory.setJpaProperties(additionalProperties());

        return entityManagerFactory;
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        return transactionManager;
    }

    private Properties additionalProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.hbm2ddl.auto", environment.getProperty("hibernate.hbm2ddl.auto"));
        properties.put("hibernate.hbm2ddl.import_files", environment.getProperty("hibernate.hbm2ddl.import_files"));
        properties.put("hibernate.hbm2ddl.import_files_sql_extractor", environment.getProperty("hibernate.hbm2ddl.import_files_sql_extractor"));
        properties.put("hibernate.dialect", environment.getProperty("hibernate.dialect"));
        properties.put("hibernate.connection.charSet", environment.getProperty("hibernate.connection.charSet"));
        properties.put("hibernate.show_sql", environment.getProperty("hibernate.show_sql"));
        properties.put("connection.autocommit", environment.getProperty("connection.autocommit"));
        return properties;
    }
}
