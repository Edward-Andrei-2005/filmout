package group02.filmout.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import group02.filmout.entity.User;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findByUserName(String userName);

    User findByEmail(String email);
}