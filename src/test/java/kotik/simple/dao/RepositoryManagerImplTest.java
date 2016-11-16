package kotik.simple.dao;

import kotik.simple.configuration.DBConfig;
import kotik.simple.dao.objects.CommandDAO;
import kotik.simple.dao.objects.User;
import kotik.simple.service.commands.CommandInterface;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

/**
 * Created by Roman_Kuznetcov on 16.11.2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DBConfig.class, loader = AnnotationConfigContextLoader.class)
public class RepositoryManagerImplTest {

    @Autowired
    RepositoryManagerImpl dao;

    @Test
    public void getUser() throws Exception {
        User user = dao.getUser("1");
        Assert.assertEquals("vasia",user.getName());
    }

    @Test
    public void addUser() throws Exception {
        dao.addUser(new User("1","vasia","displayname","stomillionovdcp"));
    }


    @Test
    public void getCommand() throws Exception {
        CommandInterface command = dao.getCommand("!help");
        Assert.assertEquals("!help",command.getName());
    }
}