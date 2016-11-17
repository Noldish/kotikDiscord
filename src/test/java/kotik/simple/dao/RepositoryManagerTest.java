package kotik.simple.dao;

import kotik.simple.configuration.DBConfig;
import kotik.simple.dao.objects.Channel;
import kotik.simple.dao.objects.CommandDAO;
import kotik.simple.dao.objects.User;
import kotik.simple.service.CommandManager;
import kotik.simple.service.commands.CommandInterface;
import kotik.simple.service.commands.TextCommand;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

/**
 * Created by Roman_Kuznetcov on 16.11.2016.
 */
@ActiveProfiles("Repository-test")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DBConfig.class, loader = AnnotationConfigContextLoader.class)
public class RepositoryManagerTest {

    @Autowired
    RepositoryManager dao;

    @Test
    public void addUser() throws Exception {
        dao.addUser(new User("999", "testUser", "displayname", "100"));
        User user = dao.getUser("999");
        Assert.assertEquals("testUser",user.getName());
        dao.deleteUser(user.getId());
    }


    @Test
    public void addCommand() throws Exception {
        dao.addCommand(new TextCommand("!testcommand","testcommandtext"));
        CommandInterface command = dao.getCommand("!testcommand");
        Assert.assertEquals(command.getName(),"!testcommand");
        dao.deleteCommand(command.getName());
    }


    @Test
    public void addChannel() throws Exception {
        dao.addChannel(new Channel("999","channelName","topictopic"));
        Channel channel = dao.getChannel("999");
        Assert.assertEquals("channelName",channel.getName());
        dao.deleteChannel(channel.getId());
    }

    @Test
    public void getAllCommands() throws Exception {

    }

    @Test
    public void getAllUsers() throws Exception {

    }

    @AfterClass
    public static void doYourOneTimeTeardown() {

    }
}