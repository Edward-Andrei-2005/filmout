package group02.filmout.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import group02.filmout.entity.Movie;
import group02.filmout.service.MovieService;

@RestController
@RequestMapping("/api/movies")
public class MovieRESTController {

    private final MovieService movieService;

    // Inyección por constructor
    public MovieRESTController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("/upcoming")
    public List<Movie> getUpcoming() {
        return movieService.getUpcomingMovies();
    }

    @GetMapping
    public List<Movie> getAll() {
        return movieService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Movie> getOne(@PathVariable int id) {
        Movie movie = movieService.findById(id);
        if (movie == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(movie);
    }

    @PostMapping
    public ResponseEntity<Movie> create(@RequestBody Movie movie) {
        movie.setHashmapId(0);
        Movie saved = movieService.save(movie);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Movie> replace(@PathVariable int id, @RequestBody Movie movie) {
        if (movieService.findById(id) == null) {
            return ResponseEntity.notFound().build();
        }
        movie.setHashmapId(id);
        Movie saved = movieService.save(movie);
        return ResponseEntity.ok(saved);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        boolean deleted = movieService.deleteById(id);
        if (!deleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}