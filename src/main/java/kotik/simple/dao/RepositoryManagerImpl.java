package kotik.simple.dao;

import kotik.simple.dao.objects.Channel;
import kotik.simple.dao.objects.CommandDAO;
import kotik.simple.dao.objects.User;
import kotik.simple.service.commands.CommandFactory;
import kotik.simple.service.commands.CommandInterface;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * Created by Roman_Kuznetcov on 16.11.2016.
 */
@Transactional
@Service
public class RepositoryManagerImpl implements RepositoryManager {

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private CommandFactory commandFactory;

    @Override
    public CommandInterface getCommand(String commandName){
        CommandDAO command = sessionFactory.getCurrentSession().get(CommandDAO.class, commandName);
        return commandFactory.createFromDBData(new DBData("commands",command.getAsMapForDBData()));
    }

    @Override
    public boolean addCommand(CommandInterface command) {
        CommandDAO commandDAO = new CommandDAO();
        commandDAO.className = command.getClass().getName();
        commandDAO.name = command.getName();
        commandDAO.description = command.getDescription();
        commandDAO.permissions = String.join(", ", command.getPermitted_userlist());
        sessionFactory.getCurrentSession().persist(commandDAO);
        return true;
    }

    @Override
    public Channel getChannel(String channelId) {
        return sessionFactory.getCurrentSession().get(Channel.class, channelId);
    }

    @Override
    public boolean addChannel(Channel channel) {
        sessionFactory.getCurrentSession().persist(channel);
        return true;
    }

    @Override
    public User getUser(String id){
        return sessionFactory.getCurrentSession().get(User.class, id);
    }

    @Override
    public boolean addUser(User user){
        sessionFactory.getCurrentSession().persist(user);
        return true;
    }

    @Override
    public Map<String, CommandInterface> getAllCommands() {
        return null;
    }

    @Override
    public Map<String, User> getAllUsers() {
        return null;
    }
}
