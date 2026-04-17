package group02.filmout.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Repository;

import group02.filmout.entity.Movie;

@Repository
public class MovieRepository {
  // Attributes
  private HashMap<Integer, Movie> mapMovies = new HashMap<>();
  private AtomicInteger nextId = new AtomicInteger(1);

  // Constructor
  public MovieRepository() {
  }

  // Methods
  public Movie save(Movie movie) {
    if (movie.getId() == 0) { 
      movie.setId(nextId.getAndIncrement()); 
    } 
    mapMovies.put(movie.getId(), movie); return movie; }

  public List<Movie> findAll() {
    return new ArrayList<>(mapMovies.values());
  }

  public Movie findById(int id) {
    return mapMovies.get(id);
  }

  public boolean saveMovie(Movie movie) {
    if (mapMovies.containsKey(movie.getId()))
      return false;

    mapMovies.put(movie.getId(), movie);
    return true;
  }

  public boolean deleteMovie(Movie movie) {
    return mapMovies.remove(movie.getId()) != null;
  }
}
