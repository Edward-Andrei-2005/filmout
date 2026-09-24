package group02.filmout.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import group02.filmout.entity.Review;
import group02.filmout.entity.User;
import group02.filmout.service.ReviewService;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/reviews")
public class ReviewRESTController {

    private final ReviewService reviewService;

    public ReviewRESTController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    // GET /api/reviews/movie/{apiId}
    @GetMapping("/movie/{apiId}")
    public List<Map<String, Object>> getByMovie(@PathVariable int apiId) {
        return reviewService.findByMovieApiId(apiId).stream()
            .map(r -> {
                Map<String, Object> m = new HashMap<>();
                m.put("id", r.getId());
                m.put("userName", r.getUser() != null ? r.getUser().getUserName() : "Anonymous");
                m.put("grade", r.getGrade());
                m.put("comment", r.getComment() != null ? r.getComment() : "");
                return m;
            }).collect(Collectors.toList());
    }

    // POST /api/reviews/movie
    @PostMapping("/movie")
    public ResponseEntity<?> addMovieReview(@RequestBody Map<String, Object> body, HttpSession session) {
        User loggedUser = (User) session.getAttribute("loggedUser");
        if (loggedUser == null) return ResponseEntity.status(401).body("Not logged in");

        int movieApiId = ((Number) body.get("movieApiId")).intValue();
        float grade = ((Number) body.get("grade")).floatValue();
        String comment = body.containsKey("comment") ? (String) body.get("comment") : "";

        Review review = new Review();
        review.setUser(loggedUser);
        review.setMovieApiId(movieApiId);
        review.setGrade(grade);
        review.setComment(comment);
        reviewService.save(review);

        Map<String, Object> resp = new HashMap<>();
        resp.put("userName", loggedUser.getUserName());
        resp.put("grade", grade);
        resp.put("comment", comment);
        return ResponseEntity.status(HttpStatus.CREATED).body(resp);
    }

    // GET /api/reviews
    @GetMapping
    public List<Review> getAll() {
        return reviewService.findAll();
    }

    // GET /api/reviews/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Review> getOne(@PathVariable int id) {
        Review review = reviewService.findById(id);
        if (review == null) {
            return ResponseEntity.notFound().build();   // 404
        }
        return ResponseEntity.ok(review);                // 200
    }

    // POST /api/reviews
    @PostMapping
    public ResponseEntity<Review> create(@RequestBody Review review) {
        review.setId(0);
        Review saved = reviewService.save(review);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);   // 201
    }

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

    // PATCH /api/reviews/{id}
    @PatchMapping("/{id}")
    public ResponseEntity<?> patch(@PathVariable int id, @RequestBody Review partial, HttpSession session) {
        User loggedUser = (User) session.getAttribute("loggedUser");
        Review existing = reviewService.findById(id);
        if (existing == null) return ResponseEntity.notFound().build();
        if (loggedUser == null || existing.getUser().getId() != loggedUser.getId()) {
            return ResponseEntity.status(403).build();
        }
        Review updated = reviewService.patch(id, partial);
        return ResponseEntity.ok(updated);
    }

    // DELETE /api/reviews/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id, HttpSession session) {
        User loggedUser = (User) session.getAttribute("loggedUser");
        Review existing = reviewService.findById(id);
        if (existing == null) return ResponseEntity.notFound().build();
        if (loggedUser == null || existing.getUser().getId() != loggedUser.getId()) {
            return ResponseEntity.status(403).build();
        }
        reviewService.deleteById(id);
        return ResponseEntity.noContent().build();       // 204
    }
}