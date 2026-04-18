package group02.filmout.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import group02.filmout.entity.Movie;
import group02.filmout.repository.MovieRepository;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import org.springframework.http.HttpHeaders; 
import org.springframework.http.HttpEntity;   
import org.springframework.web.client.RestTemplate; 
import java.util.Collections; 
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpMethod;

@Service
public class MovieService {

    // Attributes
    static final private String API_KEY = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI4OGQyMDRhNTA0M2RiYzI0NTVjMjY4NWE4ZjIyMmFiYiIsIm5iZiI6MTc3NjQzOTMxMy4yMjgsInN1YiI6IjY5ZTI1MDExYWRkNzhjZmZkMThiN2NmMiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.1sr147KxxgF3gS8zMUtmtvsKrbFXAxtM2NpFaamolWA"; 
    static final private String API_URL_UPCOMING_MOVIES = "https://api.themoviedb.org/3/movie/upcoming";
    static final private String API_URL_IMAGES = "https://image.tmdb.org/t/p/original";
    private final RestTemplate restTemplate = new RestTemplate();

    @Autowired
    private MovieRepository movieRepository;

    public List<Movie> getUpcomingMovies() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", API_KEY);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        // 1. Recibimos el JSON puro en un nodo
        ResponseEntity<JsonNode> response = restTemplate.exchange(API_URL_UPCOMING_MOVIES, HttpMethod.GET, entity, JsonNode.class);

        // 2. Accedemos directamente a la clave "results"
        JsonNode resultsNode = response.getBody().get("results");

        // 3. Convertimos ese nodo directamente a una lista de tu clase Movie
        ObjectMapper mapper = new ObjectMapper();
        try {
            // Esto mapea el array de JSON directamente a List<Movie>
            return mapper.readerForListOf(Movie.class).readValue(resultsNode);
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    public void getListMoviesSaved(List<Movie> movies) {
        for (Movie m : movies) {
            movieRepository.save(m);
        }
    }


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

    // Shouldn't be PATCH methods in a entity wich calls an API
    /*
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
    */
}