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

import java.util.HashMap;
import java.util.List;
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
    public CommandInterface getCommand(String commandName) {
        CommandDAO command = sessionFactory.getCurrentSession().get(CommandDAO.class, commandName);
        return commandFactory.createFromDBData(new DBData("commands", command.getAsMapForDBData()));
    }

    @Override
    public boolean addCommand(CommandInterface command) {
        CommandDAO commandDAO = new CommandDAO();
        commandDAO.className = command.getClass().getName();
        commandDAO.name = command.getName();
        commandDAO.description = command.getDescription();
        commandDAO.permissions = String.join(", ", command.getPermitted_userlist());
        sessionFactory.getCurrentSession().saveOrUpdate(commandDAO);
        return true;
    }

    @Override
    public Channel getChannel(String channelId) {
        return sessionFactory.getCurrentSession().get(Channel.class, channelId);
    }

    @Override
    public boolean addChannel(Channel channel) {
        sessionFactory.getCurrentSession().saveOrUpdate(channel);
        return true;
    }

    @Override
    public User getUser(String id) {
        return sessionFactory.getCurrentSession().get(User.class, id);
    }

    @Override
    public boolean addUser(User user) {
        sessionFactory.getCurrentSession().saveOrUpdate(user);
        return true;
    }

    @Override
    public Map<String, CommandInterface> getAllCommands() {
        List<CommandDAO> commands = (List<CommandDAO>) sessionFactory.getCurrentSession().createQuery("from commands").getResultList();
        Map<String, CommandInterface> result = new HashMap<>();
        for (CommandDAO command : commands) {
            result.put(command.name, commandFactory.createFromDBData(new DBData("commands", command.getAsMapForDBData())));
        }
        return result;
    }

    @Override
    public Map<String, User> getAllUsers() {
        List<User> users = (List<User>) sessionFactory.getCurrentSession().createQuery("from users").getResultList();
        Map<String, User> result = new HashMap<>();
        for (User user : users) {
            result.put(user.getId(), user);
        }
        return result;
    }

    @Override
    public Boolean deleteUser(String userId) {
        sessionFactory.getCurrentSession().delete(getUser(userId));
        return true;
    }

    @Override
    public Boolean deleteCommand(String commandName) {
        sessionFactory.getCurrentSession().delete(sessionFactory.getCurrentSession().get(CommandDAO.class, commandName));
        return true;
    }

    @Override
    public Boolean deleteChannel(String channelId) {
        sessionFactory.getCurrentSession().delete(getChannel(channelId));
        return true;
    }
}
