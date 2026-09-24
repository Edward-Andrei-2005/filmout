package group02.filmout.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import group02.filmout.entity.User;
import group02.filmout.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserRESTController {

    private final UserService userService;

    public UserRESTController(UserService userService) {
        this.userService = userService;
    }

    // GET /api/users
    @GetMapping
    public List<User> getAll() {
        return userService.findAll();
    }

    // GET /api/users/{id}
    @GetMapping("/{id}")
    public ResponseEntity<User> getOne(@PathVariable int id) {
        User user = userService.findById(id);
        if (user == null) {
            return ResponseEntity.notFound().build();   // 404
        }
        return ResponseEntity.ok(user);                // 200
    }

    // POST /api/users
    @PostMapping
    public ResponseEntity<User> create(@RequestBody User user) {
        user.setId(0);
        User saved = userService.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);   // 201
    }

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

    // PATCH /api/users/{id}
    @PatchMapping("/{id}")
    public ResponseEntity<User> patch(@PathVariable int id, @RequestBody User partial) {
        User updated = userService.patch(id, partial);
        if (updated == null) {
            return ResponseEntity.notFound().build();    // 404
        }
        return ResponseEntity.ok(updated);
    }

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