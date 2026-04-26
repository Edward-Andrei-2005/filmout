package group02.filmout.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import group02.filmout.entity.Event;
import group02.filmout.entity.User;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Integer> {

    List<Event> findByAdmin(User admin);
}
