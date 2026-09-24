package group02.filmout.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import group02.filmout.entity.Cinema;

public interface CinemaRepository extends JpaRepository<Cinema, Integer> {

}
