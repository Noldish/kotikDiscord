package kotik.simple.dao;

import kotik.simple.listener.ChatListener;
import kotik.simple.listener.InterfaceListener;
import kotik.simple.service.CommandManager;
import kotik.simple.service.DiscordService;
import kotik.simple.service.UserService;
import kotik.simple.service.commands.CommandFactory;
import org.mockito.Mockito;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

/**
 * Created by Roman_Kuznetcov on 17.11.2016.
 */
@Profile("Repository-test")
@Configuration
public class TestConfiguration {

    @Bean
    public FactoryBean<CommandManager> mockingService() {
        return new MockitoFactoryBean<>(CommandManager.class);
    }

    @Bean
    public CommandFactory factory(){return new CommandFactory();}

}
