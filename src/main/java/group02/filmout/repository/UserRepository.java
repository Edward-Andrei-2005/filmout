package group02.filmout.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Repository;

import group02.filmout.entity.User;

@Repository
public class UserRepository {
  // Attributes
  private HashMap<Integer, User> mapUsers = new HashMap<>();
  private AtomicInteger nextId = new AtomicInteger(1);

  // Constructor
  public UserRepository() {
  }

  // Methods
  public User save(User user) {
    if (user.getId() == 0) {
      user.setId(nextId.getAndIncrement());
    }
    mapUsers.put(user.getId(), user);
    return user;
  }

  public List<User> findAll() {
    return new ArrayList<>(mapUsers.values());
  }

  public User findById(int id) {
    return mapUsers.get(id);
  }

  public User findByUserName(String userName) {
    for (User u : mapUsers.values()) {
      if (u.getUserName().equalsIgnoreCase(userName)) {
        return u;
      }
    }

    return null;
  }

  public User findByEmail(String email) {
    for (User u : mapUsers.values()) {
      if (u.getEmail() != null && u.getEmail().equalsIgnoreCase(email)) {
        return u;
      }
    }
    return null;
  }

  public boolean deleteUser(int id) {
    return mapUsers.remove(id) != null;
  }
}