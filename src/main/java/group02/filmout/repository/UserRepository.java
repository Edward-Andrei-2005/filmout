package group02.filmout.repository;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.ArrayList;
import java.util.HashMap;

import group02.filmout.entity.User;

import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {
  // Attributes
  private HashMap<Integer, User> mapUsers = new HashMap<>();
  private AtomicInteger nextId = new AtomicInteger(1);
  
  // Constructor
  public UserRepository() {}

  // Methods
  public User save(User user) { 
    if (user.getId() == 0) { 
      user.setId(nextId.getAndIncrement()); 
    } 
    mapUsers.put(user.getId(), user); return user; 
  }

  public List<User> findAll() {
    return new ArrayList<>(mapUsers.values());
  }

  public User findById(int id) {
    return mapUsers.get(id);
  }

  public User findByUserName(String userName) {
    for (User u: mapUsers.values()) {
      if (u.getUserName() == userName) {
        return u;
      }
    }

    return null;
  }

  /*public boolean saveUser(User user) {
    if (mapUsers.containsKey(user.getId())) return false;

    mapUsers.put(user.getId(), user);
    return true;
  }*/

  /*public boolean deleteUser(User user) {
    return mapUsers.remove(user.getId()) != null;
  }*/
 
  public boolean deleteUser(int id) {
    return mapUsers.remove(id) != null;
  }
}