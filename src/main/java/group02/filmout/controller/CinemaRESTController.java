package group02.filmout.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import group02.filmout.entity.Cinema;
import group02.filmout.service.CinemaService;

@RestController
@RequestMapping("/api/cinemas")
public class CinemaRESTController {

    private final CinemaService cinemaService;

    public CinemaRESTController(CinemaService cinemaService) {
        this.cinemaService = cinemaService;
    }

    // GET /api/cinemas
    @GetMapping
    public List<Cinema> getAll() {
        return cinemaService.findAll();
    }

    // GET /api/cinemas/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Cinema> getOne(@PathVariable int id) {
        Cinema cinema = cinemaService.findById(id);
        if (cinema == null) {
            return ResponseEntity.notFound().build();   // 404
        }
        return ResponseEntity.ok(cinema);                // 200
    }

    // POST /api/cinemas
    @PostMapping
    public ResponseEntity<Cinema> create(@RequestBody Cinema cinema) {
        // Forzamos id=0 para que el repository le asigne uno nuevo.
        cinema.setId(0);
        Cinema saved = cinemaService.save(cinema);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);   // 201
    }

    // PUT /api/cinemas/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Cinema> replace(@PathVariable int id, @RequestBody Cinema cinema) {
        if (cinemaService.findById(id) == null) {
            return ResponseEntity.notFound().build();    // 404
        }
        cinema.setId(id);
        Cinema saved = cinemaService.save(cinema);
        return ResponseEntity.ok(saved);
    }

    // PATCH /api/cinemas/{id}
    @PatchMapping("/{id}")
    public ResponseEntity<Cinema> patch(@PathVariable int id, @RequestBody Cinema partial) {
        Cinema updated = cinemaService.patch(id, partial);
        if (updated == null) {
            return ResponseEntity.notFound().build();    // 404
        }
        return ResponseEntity.ok(updated);
    }

    // DELETE /api/cinemas/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        boolean deleted = cinemaService.deleteById(id);
        if (!deleted) {
            return ResponseEntity.notFound().build();    // 404
        }
        return ResponseEntity.noContent().build();       // 204
    }
}