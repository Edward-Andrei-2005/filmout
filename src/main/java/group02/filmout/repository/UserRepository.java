package group02.filmout.repository;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

import group02.filmout.entity.User;

import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {
  // Attributes
  private HashMap<Integer, User> users;
  
  // Constructor
  public UserRepository() {}

  // Methods
  public List<User> findAll() {
    return new ArrayList<>(users.values());
  }

  public User findById(int id) {
    return users.get(id);
  }

  public User findByUserName(String userName) {
    for (User u: users.values()) {
      if (u.getUserName() == userName) {
        return u;
      }
    }

    return null;
  }

  public boolean saveUser(User user) {
    if (users.containsKey(user.getId())) return false;

    users.put(user.getId(), user);
    return true;
  }

  public boolean deleteUser(User user) {
    return users.remove(user.getId()) != null;
  }
}