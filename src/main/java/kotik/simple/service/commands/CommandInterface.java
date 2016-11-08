package kotik.simple.service.commands;

import sx.blah.discord.handle.obj.IMessage;

import java.util.List;

/**
 * Created by Romique on 19.10.2016.
 */
public interface CommandInterface {

    public void eval(IMessage message);

    public String getName();

    public String getDescription();

    public List<String> getPermitted_userlist();

    public CommandInterface addPermission(String id);

}
