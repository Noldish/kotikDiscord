package kotik.simple.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * Created by Romique on 19.10.2016.
 */
@Configuration
@ComponentScan("kotik.simple")
@EnableWebMvc
public class AppConfig{
}