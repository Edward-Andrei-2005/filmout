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
    if (movie.getId_hashmap() == 0) {
      movie.setId_hashmap(nextId.getAndIncrement());
    }
    mapMovies.put(movie.getId_hashmap(), movie);
    return movie;
  }

  public List<Movie> findAll() {
    return new ArrayList<>(mapMovies.values());
  }

  public Movie findById(int id) {
    return mapMovies.get(id);
  }

  public Movie findByTitle(String title) {
    for (Movie m : mapMovies.values()) {
      if (m.getTitle().equalsIgnoreCase(title)) {
        return m;
      }
    }
    return null;
  }

  public List<Movie> findByGenre(String genre) {
    ArrayList<Movie> aux = new ArrayList<>();

    for (Movie m : mapMovies.values()) {
      if (m.getGenre_names().contains(genre)) {
        aux.add(m);
      }
    }

    return aux;
  }

  public boolean deleteMovie(int id) {
    return mapMovies.remove(id) != null;
  }
}
