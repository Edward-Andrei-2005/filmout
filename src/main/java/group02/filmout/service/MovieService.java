package group02.filmout.service;

import group02.filmout.entity.Movie;
import org.springframework.stereotype.Service;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class MovieService {

    private Map<Integer, Movie> movies = new ConcurrentHashMap<>();
    private AtomicInteger nextId = new AtomicInteger(1);

    // CRUD


    public Movie save(Movie movie) {
        if (movie.getId() == 0) {
            movie.setId(nextId.getAndIncrement());
        }
        movies.put(movie.getId(), movie);
        return movie;
    }

    public Collection<Movie> findAll() {
        return movies.values();
    }

    public Movie findById(int id) {
        return movies.get(id);
    }

    public Movie deleteById(int id) {
        return movies.remove(id);
    }

    //PATCH
    public Movie patch(int id, Movie updatedFields) {
        Movie existingMovie = movies.get(id);
        if (existingMovie != null) {
            if (updatedFields.getTitle() != null) existingMovie.setTitle(updatedFields.getTitle());
            if (updatedFields.getGender() != null) existingMovie.setGender(updatedFields.getGender());
            if (updatedFields.getDescription() != null) existingMovie.setDescription(updatedFields.getDescription());
            if (updatedFields.getCover() != null) existingMovie.setCover(updatedFields.getCover());
            if (updatedFields.getDuration() != 0) existingMovie.setDuration(updatedFields.getDuration());
            if (updatedFields.getYear() != 0) existingMovie.setYear(updatedFields.getYear());
            if (updatedFields.getGrade() != 0) existingMovie.setGrade(updatedFields.getGrade());
        }
        return existingMovie;
    }
}