package kotik.simple.service;

import kotik.simple.dao.RepositoryManager;
import kotik.simple.dao.objects.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Romique on 03.11.2016.
 */
@Service
public class UserService {
    private Map<String, User> users = new HashMap<>();

    @Autowired
    RepositoryManager repository;

    @PostConstruct
    public void init(){
        this.users = repository.getAllUsers();
        System.out.println("Loaded " + users.size() + " users from DB");
    }

    public void addUser(User user){
        if (!users.containsKey(user.getId())){
            users.put(user.getId(), user);
            repository.addUser(user);
        }
    }

    public void updateInfo(User user){
        if (users.containsKey(user.getId())){
            users.get(user.getId()).setName(user.getName());
            users.get(user.getId()).setDisplayName(user.getDisplayName());
            repository.addUser(users.get(user.getId()));
        }
    }

    public void upsertUser(User user){
        if (users.containsKey(user.getId())){
            users.remove(user.getId());
        }
        users.put(user.getId(), user);
        repository.addUser(user);
    }

    public User getUserByName(String name){
        for (Map.Entry<String, User> e : users.entrySet()){
            if (e.getValue().getName().equals(name)){
                return e.getValue();
            }
        }
        return null;
    }

    public void addDcp(String name, String dcp){
        User user = getUserByName(name);
        user.addDcp(Integer.parseInt(dcp));
        repository.addUser(user);
    }

    public Map<String, User> getUsers() {
        return users;
    }

    public void setUsers(Map<String, User> users) {
        this.users = users;
    }
}
