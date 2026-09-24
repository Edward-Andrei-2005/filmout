package group02.filmout.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Repository;

import group02.filmout.entity.Movie;

@Repository
public class MovieRepository {

    // Programar hacia la interfaz Map
    private final Map<Integer, Movie> mapMovies = new HashMap<>();
    private final AtomicInteger nextId = new AtomicInteger(1);

    public MovieRepository() {}

    public Movie save(Movie movie) {
        if (movie.getHashmapId() == 0) {
            movie.setHashmapId(nextId.getAndIncrement());
        }
        mapMovies.put(movie.getHashmapId(), movie);
        return movie;
    }

    public List<Movie> findAll() {
        return new ArrayList<>(mapMovies.values());
    }

    public Movie findById(int id) {
        return mapMovies.get(id);
    }

    public Movie findByTitle(String title) {
        return mapMovies.values().stream()
                .filter(m -> m.getTitle().equalsIgnoreCase(title))
                .findFirst()
                .orElse(null);
    }

    public List<Movie> findByGenre(String genre) {
        List<Movie> aux = new ArrayList<>();
        for (Movie m : mapMovies.values()) {
            if (m.getGenreNames().contains(genre)) {
                aux.add(m);
            }
        }
        return aux;
    }

    public boolean deleteMovie(int id) {
        return mapMovies.remove(id) != null;
    }
}
