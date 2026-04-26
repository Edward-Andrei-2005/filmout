package group02.filmout.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import group02.filmout.entity.Review;
import group02.filmout.entity.User;
import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Integer> {

    List<Review> findByUser(User user);

    List<Review> findByMovieApiId(int movieApiId);
}
