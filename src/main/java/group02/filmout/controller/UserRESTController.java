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

import group02.filmout.entity.User;
import group02.filmout.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserRESTController {

    @Autowired
    private UserService userService;

    // ─── READ (todos) ────────────────────────────────────────────
    // GET /api/users
    @GetMapping
    public List<User> getAll() {
        return (List<User>) userService.findAll();
    }

    // ─── READ (uno) ──────────────────────────────────────────────
    // GET /api/users/{id}
    @GetMapping("/{id}")
    public ResponseEntity<User> getOne(@PathVariable int id) {
        User user = userService.findById(id);
        if (user == null) {
            return ResponseEntity.notFound().build();   // 404
        }
        return ResponseEntity.ok(user);                // 200
    }

    // ─── CREATE ──────────────────────────────────────────────────
    // POST /api/users
    @PostMapping
    public ResponseEntity<User> create(@RequestBody User user) {
        user.setId(0);
        User saved = userService.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);   // 201
    }

    // ─── UPDATE (reemplazo completo) ─────────────────────────────
    // PUT /api/users/{id}
    @PutMapping("/{id}")
    public ResponseEntity<User> replace(@PathVariable int id, @RequestBody User user) {
        if (userService.findById(id) == null) {
            return ResponseEntity.notFound().build();    // 404
        }
        user.setId(id);
        User saved = userService.save(user);
        return ResponseEntity.ok(saved);
    }

    // ─── PATCH (actualizacion parcial) ───────────────────────────
    // PATCH /api/users/{id}
    @PatchMapping("/{id}")
    public ResponseEntity<User> patch(@PathVariable int id, @RequestBody User partial) {
        User updated = userService.patch(id, partial);
        if (updated == null) {
            return ResponseEntity.notFound().build();    // 404
        }
        return ResponseEntity.ok(updated);
    }

    // ─── DELETE ──────────────────────────────────────────────────
    // DELETE /api/users/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        boolean deleted = userService.deleteById(id);
        if (!deleted) {
            return ResponseEntity.notFound().build();    // 404
        }
        return ResponseEntity.noContent().build();       // 204
    }
}