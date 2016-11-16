package kotik.simple.dao;

import kotik.simple.objects.AbstractCommand;
import kotik.simple.objects.User;
import kotik.simple.service.commands.CommandInterface;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Created by Roman_Kuznetcov on 16.11.2016.
 */
@Transactional
@Service
public class DAOClass{

    @Autowired
    private SessionFactory sessionFactory;

    public CommandInterface getCommand(String commandName){
        CommandInterface command = sessionFactory.getCurrentSession().get(AbstractCommand.class, commandName);
        return command;
    }

    public User getUser(String id){
        return sessionFactory.getCurrentSession().get(User.class, id);
    }

    public boolean addUser(User user){
        sessionFactory.getCurrentSession().persist(user);
        return true;
    }

}
