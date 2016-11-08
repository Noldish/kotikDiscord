package kotik.simple.service.commands;

import kotik.simple.service.CommandManager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Romique on 04.11.2016.
 */
public abstract class AbstractCommand implements CommandInterface, Serializable{
    private static String TABLE = "textcommands";
    private String name;
    private String description;
    private List<String> permitted_userlist = new ArrayList<>();
    private CommandManager commandManager;

    public AbstractCommand(){

    }

    public AbstractCommand(String name, String description){
        this.name = name;
        this.description = description;
    }

    public String toString(){
        return description;
    }

    public CommandManager getCommandManager() {
        return commandManager;
    }

    public AbstractCommand setCommandManager(CommandManager commandManager) {
        this.commandManager = commandManager;
        return this;
    }

    @Override
    public String getName() {
        return name;
    }

    public AbstractCommand setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public AbstractCommand addPermission(String id){
        this.permitted_userlist.add(id);
        return this;
    }

    public List<String> getPermitted_userlist() {
        return permitted_userlist;
    }
}
