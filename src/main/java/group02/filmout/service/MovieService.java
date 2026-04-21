package group02.filmout.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import group02.filmout.entity.Movie;
import group02.filmout.repository.MovieRepository;
import jakarta.annotation.PostConstruct;
import tools.jackson.databind.JsonNode;

@Service
public class MovieService {

    // Attributes
    static final private String API_KEY = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI4OGQyMDRhNTA0M2RiYzI0NTVjMjY4NWE4ZjIyMmFiYiIsIm5iZiI6MTc3NjQzOTMxMy4yMjgsInN1YiI6IjY5ZTI1MDExYWRkNzhjZmZkMThiN2NmMiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.1sr147KxxgF3gS8zMUtmtvsKrbFXAxtM2NpFaamolWA"; 
    static final private String API_URL_UPCOMING_MOVIES = "https://api.themoviedb.org/3/movie/upcoming";
    static final private String API_URL_GENRE_LIST = "https://api.themoviedb.org/3/genre/movie/list";
    static final private String API_URL_IMAGES = "https://image.tmdb.org/t/p/original";
    static private HashMap<Integer, String> mapGenres = new HashMap<>();
    private final RestTemplate restTemplate = new RestTemplate();

    @Autowired
    private MovieRepository movieRepository;

    // Method will only be called once when the app is started
    @PostConstruct
    public void init_genres() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + API_KEY);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<JsonNode> response = restTemplate.exchange(
            API_URL_GENRE_LIST,
            HttpMethod.GET,
            entity,
            JsonNode.class
        );

        JsonNode root = response.getBody();

        // We look for the "genres" list inside the JSON
        JsonNode results = root.get("genres");

        // 4. Looping through the JSON array manually
        if (results != null && results.isArray()) {
            for (JsonNode genre : results) {
                String name = genre.get("name").asString();
                int id = genre.get("id").asInt();
                
                mapGenres.put(id, name);
            }
        }

    }

    public int genreToId(String name) {
        for (int id : mapGenres.keySet()) {
            if (mapGenres.get(id).equals(name)) {
                return id;
            }
        }
        return -1; // Genre not found
    }

    public String idToGenre(int id) {
        return mapGenres.get(id);
    }


    public List<Movie> getUpcomingMovies() {
        // 1. Config the call (same as before)
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + API_KEY);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        // 2. We ask for the response as a JsonNode (the whole tree)
        ResponseEntity<JsonNode> response = restTemplate.exchange(
            API_URL_UPCOMING_MOVIES,
            HttpMethod.GET,
            entity,
            JsonNode.class
        );

        JsonNode root = response.getBody();
        List<Movie> listaPeliculas = new ArrayList<>();

        // 3. We navigate manually to the "results" list
        JsonNode results = root.get("results");

        // 4. Looping through the JSON array manually
        if (results != null && results.isArray()) {
            for (JsonNode atribute : results) {
                Movie m = new Movie();
                
                // We extract the data from the node and assign it
                m.setAdult(atribute.get("adult").asBoolean());
                m.setBackdrop_path(atribute.get("backdrop_path").asString());

                ArrayList<String> genre_names = new ArrayList<>();
                for (JsonNode genreIdNode : atribute.get("genre_ids")) {
                    genre_names.add(idToGenre(genreIdNode.asInt()));
                }
                m.setGenre_names(genre_names);

                m.setId_api(atribute.get("id").asInt());
                m.setOriginal_language(atribute.get("original_language").asString());
                m.setOriginal_title(atribute.get("original_title").asString());
                m.setOverview(atribute.get("overview").asString());
                m.setPopularity(atribute.get("popularity").asInt());
                m.setPoster_path(atribute.get("poster_path").asString());
                m.setRelease_date(atribute.get("release_date").asString());
                m.setTitle(atribute.get("title").asString());
                m.setVideo(atribute.get("video").asBoolean());
                m.setVote_average((float) atribute.get("vote_average").asDouble());
                m.setVote_count(atribute.get("vote_count").asInt());

                // We add the movie to our list
                listaPeliculas.add(m);
            }
        }

        return listaPeliculas;
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

}