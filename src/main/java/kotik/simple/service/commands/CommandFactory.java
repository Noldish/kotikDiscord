package kotik.simple.service.commands;

import kotik.simple.dao.DBData;
import kotik.simple.service.CommandManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Romique on 08.11.2016.
 */
@Service
public class CommandFactory {

    @Autowired
    @Lazy
    CommandManager commandManager;

    public AbstractCommand create(String name, Class<? extends AbstractCommand> clazz){
        try {
            return clazz.newInstance().setCommandManager(commandManager).setName(name);
        } catch (InstantiationException | IllegalAccessException e){
            e.printStackTrace();
        }
        return null;
    }

    public CommandInterface create(Class<? extends AbstractCommand> clazz){
        try {
            return clazz.newInstance().setCommandManager(commandManager);
        } catch (InstantiationException | IllegalAccessException e){
            e.printStackTrace();
        }
        return null;
    }

    public CommandInterface create(Class<? extends  AbstractCommand> clazz, Map params){
        try {
            return clazz.getConstructor(params.getClass()).newInstance(params).setCommandManager(commandManager);
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e){
            e.printStackTrace();
        }
        return null;
    }

    public CommandInterface createTextCommand(String name, String description){
        Map o = new HashMap<String, String>();
        o.put("name", name);
        o.put("description", description);
        return create(TextCommand.class, o);
    }

    public CommandInterface createFromDBData(DBData dbData){
        try {
            String classname = dbData.get("class");
            Map o = new HashMap<>();
            for (Map.Entry e : dbData.getValues().entrySet()){
                if (!e.getKey().equals("class")){
                    o.put(e.getKey(), e.getValue());
                }
            }
            Class clazz = Class.forName(classname);
            if (o.size()>0) {
                return create(clazz, o);
            } else {
                return create(clazz);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
