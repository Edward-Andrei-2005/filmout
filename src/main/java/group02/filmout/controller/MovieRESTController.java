package group02.filmout.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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

    @Autowired
    private MovieService movieService;

    // ─── GET UPCOMING MOVIES ─────────────────────────────────────
    // GET /api/movies/upcoming
    @GetMapping("/upcoming")
    public List<Movie> getUpcoming() {
        return movieService.getUpcomingMovies();
    }

    // ─── READ (todos) ────────────────────────────────────────────
    // GET /api/movies
    @GetMapping
    public List<Movie> getAll() {
        return movieService.findAll();
    }

    // ─── READ (uno) ──────────────────────────────────────────────
    // GET /api/movies/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Movie> getOne(@PathVariable int id) {
        Movie movie = movieService.findById(id);
        if (movie == null) {
            return ResponseEntity.notFound().build();   // 404
        }
        return ResponseEntity.ok(movie);                // 200
    }

    // ─── CREATE ──────────────────────────────────────────────────
    // POST /api/movies
    @PostMapping
    public ResponseEntity<Movie> create(@RequestBody Movie movie) {
        movie.setId_hashmap(0);
        Movie saved = movieService.save(movie);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);   // 201
    }

    // ─── UPDATE (reemplazo completo) ─────────────────────────────
    // PUT /api/movies/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Movie> replace(@PathVariable int id, @RequestBody Movie movie) {
        if (movieService.findById(id) == null) {
            return ResponseEntity.notFound().build();    // 404
        }
        movie.setId_hashmap(id);
        Movie saved = movieService.save(movie);
        return ResponseEntity.ok(saved);
    }

    // ─── DELETE ──────────────────────────────────────────────────
    // DELETE /api/movies/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        boolean deleted = movieService.deleteById(id);
        if (!deleted) {
            return ResponseEntity.notFound().build();    // 404
        }
        return ResponseEntity.noContent().build();       // 204
    }
}