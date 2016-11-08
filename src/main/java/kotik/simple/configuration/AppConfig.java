package kotik.simple.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


/**
 * Created by Romique on 19.10.2016.
 */
@Configuration
@ComponentScan("kotik.simple")
@EnableWebMvc
@PropertySource("classpath:application.properties")
public class AppConfig{
    @Bean
    public static PropertySourcesPlaceholderConfigurer configurer(){
        return new PropertySourcesPlaceholderConfigurer();
    }
}
