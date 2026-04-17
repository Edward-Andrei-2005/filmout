package group02.filmout.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import group02.filmout.entity.Movie;
import group02.filmout.repository.MovieRepository;

@Service
public class MovieService {

    // Attributes
    @Autowired
    private MovieRepository movieRepository;

    // CRUD
    public Movie save(Movie movie) {
        return movieRepository.save(movie);
    }

    public List<Movie> findAll() {
        return movieRepository.findAll();
    }

    public Movie findById(int id) {
        return movieRepository.findById(id);
    }

    public boolean deleteById(int id) {
        return movieRepository.deleteMovie(id);
    }

    //PATCH
    public Movie patch(int id, Movie updatedFields) {
        Movie existingMovie = movieRepository.findById(id);
        if (existingMovie != null) {
            if (updatedFields.getTitle() != null) existingMovie.setTitle(updatedFields.getTitle());
            if (updatedFields.getGender() != null) existingMovie.setGender(updatedFields.getGender());
            if (updatedFields.getDescription() != null) existingMovie.setDescription(updatedFields.getDescription());
            if (updatedFields.getCover() != null) existingMovie.setCover(updatedFields.getCover());
            if (updatedFields.getDuration() != 0) existingMovie.setDuration(updatedFields.getDuration());
            if (updatedFields.getYear() != 0) existingMovie.setYear(updatedFields.getYear());
            if (updatedFields.getGrade() != 0) existingMovie.setGrade(updatedFields.getGrade());
        }
        return movieRepository.save(existingMovie);
    }
}