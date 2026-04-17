package group02.filmout.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import group02.filmout.entity.Review;
import group02.filmout.repository.ReviewRepository;

@Service
public class ReviewService {

    // Attributes
    @Autowired
    private ReviewRepository reviewRepository;

    // CRUD
    public Review save(Review review) {
        return reviewRepository.save(review);
    }

    public List<Review> findAll() {
        return reviewRepository.findAll();
    }

    public Review findById(int id) {
        return reviewRepository.findById(id);
    }

    public boolean deleteById(int id) {
        return reviewRepository.deleteReview(id);
    }

    //PATCH
    public Review patch(int id, Review updatedFields) {
        Review existingReview = reviewRepository.findById(id);
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
        return reviewRepository.save(existingReview);
    }
}