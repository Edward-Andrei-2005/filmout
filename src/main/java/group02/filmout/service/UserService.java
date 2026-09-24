package group02.filmout.service;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import group02.filmout.entity.User;
import group02.filmout.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User save(User user) {
        if (user.getPassword() != null && !user.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        return userRepository.save(user);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(int id) {
        return userRepository.findById(id).orElse(null);
    }

    public boolean deleteById(int id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public User findByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User patch(int id, User updatedFields) {
        User existingUser = findById(id);
        if (existingUser != null) {
            if (updatedFields.getUserName() != null) {
                existingUser.setUserName(updatedFields.getUserName());
            }
            if (updatedFields.getEmail() != null) {
                existingUser.setEmail(updatedFields.getEmail());
            }
            if (updatedFields.getPassword() != null && !updatedFields.getPassword().isBlank()) {
                existingUser.setPassword(passwordEncoder.encode(updatedFields.getPassword()));
            }
            return userRepository.save(existingUser);
        }
        return null;
    }
}
