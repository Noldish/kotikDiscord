package kotik.simple.objects;

import kotik.simple.service.CommandManager;
import kotik.simple.service.commands.CommandInterface;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Romique on 04.11.2016.
 */
@MappedSuperclass
@Table(name="commands",schema = "public")
public abstract class AbstractCommand implements CommandInterface, Serializable{

    private String className;
    private String name;
    private String description;
    private List<String> permitted_userlist = new ArrayList<>();
    private CommandManager commandManager;

    private static String TABLE = "textcommands";

    public AbstractCommand(){
    }

    public AbstractCommand(String name, String description){
        this.className = this.getClass().getName();
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


    @NotEmpty
    @Column(name="class", nullable=false)
    public String getClassName() {
        return className;
    }

    @Id
    @Override
    @NotEmpty
    @Column(name="name", unique=true, nullable=false)
    public String getName() {
        return name;
    }

    @Override
    @NotEmpty
    @Column(name="description", nullable=false)
    public String getDescription() {
        return description;
    }

    @NotEmpty
    @Column(name="permissions", nullable=false)
    public List<String> getPermitted_userlist() {
        return permitted_userlist;
    }


}
