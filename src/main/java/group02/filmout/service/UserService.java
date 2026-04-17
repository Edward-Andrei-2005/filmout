package group02.filmout.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import group02.filmout.entity.User;
import group02.filmout.repository.UserRepository;

@Service
public class UserService {

    // Attributes
    @Autowired
    private UserRepository userRepository;

    // CRUD
    public User save(User user) {
        return userRepository.save(user);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(int id) {
        return userRepository.findById(id);
    }

    public boolean deleteById(int id) {
        return userRepository.deleteUser(id);
    }

    //PATCH
    public User patch(int id, User updatedFields) {
        User existingUser = userRepository.findById(id);
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
        return userRepository.save(existingUser);
    }
}
