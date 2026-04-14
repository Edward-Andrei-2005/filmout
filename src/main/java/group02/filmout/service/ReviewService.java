package group02.filmout.service;

import group02.filmout.entity.Review;
import org.springframework.stereotype.Service;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class ReviewService {

    private Map<Integer, Review> reviews = new ConcurrentHashMap<>();
    private AtomicInteger nextId = new AtomicInteger(1);

    // CRUD


    public Review save(Review review) {
        if (review.getId() == 0) {
            review.setId(nextId.getAndIncrement());
        }
        reviews.put(review.getId(), review);
        return review;
    }

    public Collection<Review> findAll() {
        return reviews.values();
    }

    public Review findById(int id) {
        return reviews.get(id);
    }

    public Review deleteById(int id) {
        return reviews.remove(id);
    }

    //PATCH
    public Review patch(int id, Review updatedFields) {
        Review existingReview = reviews.get(id);
        if (existingReview != null) {
            if (updatedFields.getGrade() != 0) {
                existingReview.setGrade(updatedFields.getGrade());
            }
            // En la Parte I, si se pasan objetos User o Movie nuevos, se actualizan las referencias
            if (updatedFields.getUser() != null) {
                existingReview.setUser(updatedFields.getUser());
            }
            if (updatedFields.getMovie() != null) {
                existingReview.setMovie(updatedFields.getMovie());
            }
        }
        return existingReview;
    }
}