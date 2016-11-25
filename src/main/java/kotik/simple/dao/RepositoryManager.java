package kotik.simple.dao;

import kotik.simple.dao.objects.Channel;
import kotik.simple.dao.objects.Sound;
import kotik.simple.dao.objects.User;
import kotik.simple.service.commands.CommandInterface;

import java.util.List;
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

    public Map<String, CommandInterface> getAllCommands();

    public Map<String, User> getAllUsers();

    public Boolean deleteUser(String userId);

    public Boolean deleteCommand(String commandName);

    public Boolean deleteChannel(String channelId);

    public Sound getSound(String name);

    public boolean addSound(Sound sound);

    public List<Sound> getAllSounds();
}
