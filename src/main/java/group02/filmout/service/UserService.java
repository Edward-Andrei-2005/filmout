package group02.filmout.service;

import group02.filmout.entity.User;
import org.springframework.stereotype.Service;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class UserService {

    private Map<Integer, User> users = new ConcurrentHashMap<>();
    private AtomicInteger nextId = new AtomicInteger(1);

    // CRUD


    public User save(User user) {
        if (user.getId() == 0) {
            user.setId(nextId.getAndIncrement());
        }
        users.put(user.getId(), user);
        return user;
    }

    public Collection<User> findAll() {
        return users.values();
    }

    public User findById(int id) {
        return users.get(id);
    }

    public User deleteById(int id) {
        return users.remove(id);
    }

    //PATCH
    public User patch(int id, User updatedFields) {
        User existingUser = users.get(id);
        if (existingUser != null) {
            if (updatedFields.getUserName() != null) {
                existingUser.setUserName(updatedFields.getUserName());
            }
            if (updatedFields.getEmail() != null) {
                existingUser.setEmail(updatedFields.getEmail());
            }
            if (updatedFields.getPassword() != null) {
                existingUser.setPassword(updatedFields.getPassword());
            }
        }
        return existingUser;
    }
}
