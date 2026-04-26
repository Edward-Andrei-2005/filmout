package group02.filmout.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import group02.filmout.entity.User;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    // Spring creará automáticamente el código para estas búsquedas
    User findByUserName(String userName);

    User findByEmail(String email);

    // Nota: findAll(), save(), deleteById() y findById()
    // ya vienen incluidos en JpaRepository.
}