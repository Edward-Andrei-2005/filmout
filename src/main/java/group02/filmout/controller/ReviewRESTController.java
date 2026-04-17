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

import group02.filmout.entity.Review;
import group02.filmout.service.ReviewService;

@RestController
@RequestMapping("/api/reviews")
public class ReviewRESTController {

    @Autowired
    private ReviewService reviewService;

    // ─── READ (todos) ────────────────────────────────────────────
    // GET /api/reviews
    @GetMapping
    public List<Review> getAll() {
        return reviewService.findAll();
    }

    // ─── READ (uno) ──────────────────────────────────────────────
    // GET /api/reviews/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Review> getOne(@PathVariable int id) {
        Review review = reviewService.findById(id);
        if (review == null) {
            return ResponseEntity.notFound().build();   // 404
        }
        return ResponseEntity.ok(review);                // 200
    }

    // ─── CREATE ──────────────────────────────────────────────────
    // POST /api/reviews
    @PostMapping
    public ResponseEntity<Review> create(@RequestBody Review review) {
        review.setId(0);
        Review saved = reviewService.save(review);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);   // 201
    }

    // ─── UPDATE (reemplazo completo) ─────────────────────────────
    // PUT /api/reviews/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Review> replace(@PathVariable int id, @RequestBody Review review) {
        if (reviewService.findById(id) == null) {
            return ResponseEntity.notFound().build();    // 404
        }
        review.setId(id);
        Review saved = reviewService.save(review);
        return ResponseEntity.ok(saved);
    }

    // ─── PATCH (actualizacion parcial) ───────────────────────────
    // PATCH /api/reviews/{id}
    @PatchMapping("/{id}")
    public ResponseEntity<Review> patch(@PathVariable int id, @RequestBody Review partial) {
        Review updated = reviewService.patch(id, partial);
        if (updated == null) {
            return ResponseEntity.notFound().build();    // 404
        }
        return ResponseEntity.ok(updated);
    }

    // ─── DELETE ──────────────────────────────────────────────────
    // DELETE /api/reviews/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        boolean deleted = reviewService.deleteById(id);
        if (!deleted) {
            return ResponseEntity.notFound().build();    // 404
        }
        return ResponseEntity.noContent().build();       // 204
    }
}