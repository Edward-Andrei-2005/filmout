package group02.filmout.service;

import java.util.List;
import java.util.Optional; // Importante añadir esto

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import group02.filmout.entity.User;
import group02.filmout.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public User save(User user) {
        // Solo encriptamos si la contraseña no está ya encriptada (opcional según vuestra lógica)
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(int id) {
        // JpaRepository devuelve Optional. Con .orElse(null) hacemos que
        // funcione igual que vuestro código antiguo.
        return userRepository.findById(id).orElse(null);
    }

    public boolean deleteById(int id) {
        // En JPA se llama deleteById. Verificamos si existe antes de borrar.
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

    // PATCH mejorado para JPA
    public User patch(int id, User updatedFields) {
        User existingUser = findById(id); // Usamos nuestro método que ya tiene el orElse(null)
        if (existingUser != null) {
            if (updatedFields.getUserName() != null) {
                existingUser.setUserName(updatedFields.getUserName());
            }
            if (updatedFields.getEmail() != null) {
                existingUser.setEmail(updatedFields.getEmail());
            }
            // Si cambian la password, hay que volver a encriptarla
            if (updatedFields.getPassword() != null && !updatedFields.getPassword().isBlank()) {
                existingUser.setPassword(passwordEncoder.encode(updatedFields.getPassword()));
            }
            return userRepository.save(existingUser);
        }
        return null;
    }
}
