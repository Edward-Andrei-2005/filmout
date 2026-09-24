package group02.filmout.service;

import java.util.List;

import org.springframework.stereotype.Service;

import group02.filmout.entity.Review;
import group02.filmout.repository.ReviewRepository;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public Review save(Review review) {
        return reviewRepository.save(review);
    }

    public List<Review> findAll() {
        return reviewRepository.findAll();
    }

    public Review findById(int id) {
        return reviewRepository.findById(id).orElse(null);
    }

    public List<Review> findByMovieApiId(int movieApiId) {
        return reviewRepository.findByMovieApiId(movieApiId);
    }

    public boolean deleteById(int id) {
        if (reviewRepository.existsById(id)) {
            reviewRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Review patch(int id, Review updatedFields) {
        Review existingReview = findById(id);
        if (existingReview == null) return null;

        // Al usar float, un 0 significa que no se ha enviado nota o que la nota es 0.
        if (updatedFields.getGrade() != 0) {
            existingReview.setGrade(updatedFields.getGrade());
        }
        if (updatedFields.getUser() != null) {
            existingReview.setUser(updatedFields.getUser());
        }
        if (updatedFields.getMovie() != null) {
            existingReview.setMovie(updatedFields.getMovie());
        }
        if (updatedFields.getComment() != null) {
            existingReview.setComment(updatedFields.getComment());
        }
        return reviewRepository.save(existingReview);
    }
}
