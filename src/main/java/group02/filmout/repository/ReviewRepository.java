package group02.filmout.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Repository;

import group02.filmout.entity.Review;
import group02.filmout.entity.User;
import group02.filmout.entity.Movie;

@Repository
public class ReviewRepository {
  // Attributes
  private HashMap<Integer, Review> mapReviews = new HashMap<>();
  private AtomicInteger nextId = new AtomicInteger(1);

  // Constructor
  public ReviewRepository() {};

  // Methods
  public Review save(Review review) { 
    if (review.getId() == 0) { 
      review.setId(nextId.getAndIncrement()); 
    } 
    mapReviews.put(review.getId(), review); return review; }
    
  public List<Review> findAll() {
    return new ArrayList<>(mapReviews.values());
  }

  public Review findById(int id) {
    return mapReviews.get(id);
  }

  public List<Review> findByUser(User user) {
    ArrayList<Review> aux = new ArrayList<>();

    for (Review r : mapReviews.values()) {
      if (r.getUser().equals(user)) {
        aux.add(r);
      }
    }

    return aux;
  }

  public List<Review> findByMovie(Movie movie) {
    ArrayList<Review> aux = new ArrayList<>();

    for (Review r : mapReviews.values()) {
      if (r.getMovie().equals(movie)) {
        aux.add(r);
      }
    }

    return aux;
  }

  public boolean saveReview(Review review) {
    if (mapReviews.containsKey(review.getId())) return false;

    mapReviews.put(review.getId(), review);
    return true;
  }

  public boolean deleteReview(Review review) {
    return mapReviews.remove(review.getId()) != null;
  }
}