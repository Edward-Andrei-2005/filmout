package group02.filmout.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import group02.filmout.entity.Movie;
import group02.filmout.repository.MovieRepository;
import jakarta.annotation.PostConstruct;
import com.fasterxml.jackson.databind.JsonNode; // Corrección del import de Jackson

@Service
public class MovieService {

    // Extraemos la API Key a application.properties por seguridad
    @Value("${tmdb.api.key}")
    private String apiKey;

    private static final String API_URL_UPCOMING_MOVIES = "https://api.themoviedb.org/3/movie/upcoming";
    private static final String API_URL_GENRE_LIST = "https://api.themoviedb.org/3/genre/movie/list";

    private static final Map<Integer, String> mapGenres = new HashMap<>();
    private final RestTemplate restTemplate = new RestTemplate();
    private final MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }


    public String idToGenre(int id) {
        return mapGenres.get(id);
    }

    @PostConstruct
    public void initGenres() {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + apiKey);
            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<String> response = restTemplate.exchange(
                    API_URL_GENRE_LIST, HttpMethod.GET, entity, String.class
            );

            // Parseamos el String
            com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();
            com.fasterxml.jackson.databind.JsonNode root = mapper.readTree(response.getBody());

            if (root != null && root.has("genres")) {
                com.fasterxml.jackson.databind.JsonNode results = root.get("genres");
                if (results.isArray()) {
                    for (com.fasterxml.jackson.databind.JsonNode genre : results) {
                        mapGenres.put(genre.get("id").asInt(), genre.get("name").asText());
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Error al inicializar los géneros: " + e.getMessage());
        }
    }

    public List<Movie> getUpcomingMovies() {
        List<Movie> upcomingMovies = new ArrayList<>();

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + apiKey);
            HttpEntity<String> entity = new HttpEntity<>(headers);

            // Pedimos un String
            ResponseEntity<String> response = restTemplate.exchange(
                    API_URL_UPCOMING_MOVIES, HttpMethod.GET, entity, String.class
            );

            // Parseamos el String
            com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();
            com.fasterxml.jackson.databind.JsonNode root = mapper.readTree(response.getBody());

            if (root != null && root.has("results")) {
                com.fasterxml.jackson.databind.JsonNode results = root.get("results");
                if (results.isArray()) {
                    for (com.fasterxml.jackson.databind.JsonNode attribute : results) {
                        Movie m = new Movie();

                        m.setAdult(attribute.get("adult").asBoolean());
                        m.setBackdropPath(attribute.get("backdrop_path").asText(""));

                        List<String> genreNames = new ArrayList<>();
                        for (com.fasterxml.jackson.databind.JsonNode genreIdNode : attribute.get("genre_ids")) {
                            genreNames.add(idToGenre(genreIdNode.asInt()));
                        }
                        m.setGenreNames(genreNames);

                        m.setApiId(attribute.get("id").asInt());
                        m.setOriginalLanguage(attribute.get("original_language").asText(""));
                        m.setOriginalTitle(attribute.get("original_title").asText(""));
                        m.setOverview(attribute.get("overview").asText(""));
                        m.setPopularity(attribute.get("popularity").asInt());
                        m.setPosterPath(attribute.get("poster_path").asText(""));
                        m.setReleaseDate(attribute.get("release_date").asText(""));
                        m.setTitle(attribute.get("title").asText(""));
                        m.setVideo(attribute.get("video").asBoolean());
                        m.setVoteAverage((float) attribute.get("vote_average").asDouble());
                        m.setVoteCount(attribute.get("vote_count").asInt());

                        upcomingMovies.add(m);
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Error al obtener las películas: " + e.getMessage());
        }

        return upcomingMovies;
    }


    public Movie save(Movie movie) { return movieRepository.save(movie); }
    public List<Movie> findAll() { return movieRepository.findAll(); }
    public Movie findById(int id) { return movieRepository.findById(id); }
    public boolean deleteById(int id) { return movieRepository.deleteMovie(id); }
}