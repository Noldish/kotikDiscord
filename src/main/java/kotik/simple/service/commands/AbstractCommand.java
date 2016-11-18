package kotik.simple.service.commands;

import kotik.simple.service.CommandManager;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Romique on 04.11.2016.
 */
    public abstract class AbstractCommand implements CommandInterface, Serializable{

    private String className;
    private String name;
    private String description;
    private List<String> permitted_userlist = new ArrayList<>();
    private CommandManager commandManager;

    public AbstractCommand(){
    }

    public AbstractCommand(String name, String description){
        this.className = this.getClass().getName();
        this.name = name;
        this.description = description;
    }

    public AbstractCommand(String name, String description, String permissions){
        this.className = this.getClass().getName();
        this.name = name;
        this.description = description;
        if (permissions!=null)
        this.permitted_userlist = Arrays.asList(permissions.split(", "));
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

    public AbstractCommand setName(String name) {
        this.name = name;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public AbstractCommand addPermission(String id){
        this.permitted_userlist.add(id);
        return this;
    }

    public String getClassName() {
        return className;
    }


    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    public List<String> getPermitted_userlist() {
        return permitted_userlist;
    }


}
