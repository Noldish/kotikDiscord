package kotik.simple.dao;

import kotik.simple.dao.objects.Channel;
import kotik.simple.dao.objects.User;
import kotik.simple.service.commands.CommandInterface;

import java.util.Map;

/**
 * Created by Roman_Kuznetcov on 16.11.2016.
 */
public interface RepositoryManager {

    public User getUser(String userId);
    public boolean addUser(User user);
    public CommandInterface getCommand(String commandName);
    public boolean addCommand(CommandInterface command);
    public Channel getChannel(String channelId);
    public boolean addChannel(Channel channel);
    public Map<String, CommandInterface>  getAllCommands();
    public Map<String, User> getAllUsers();
}
