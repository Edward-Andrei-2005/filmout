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

import group02.filmout.entity.Event;
import group02.filmout.service.EventService;

@RestController
@RequestMapping("/api/events")
public class EventRESTController {

    @Autowired
    private EventService eventService;

    // ─── READ (todos) ────────────────────────────────────────────
    // GET /api/events
    @GetMapping
    public List<Event> getAll() {
        return eventService.findAll();
    }

    // ─── READ (uno) ──────────────────────────────────────────────
    // GET /api/events/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Event> getOne(@PathVariable int id) {
        Event event = eventService.findById(id);
        if (event == null) {
            return ResponseEntity.notFound().build();   // 404
        }
        return ResponseEntity.ok(event);                // 200
    }

    // ─── CREATE ──────────────────────────────────────────────────
    // POST /api/events
    @PostMapping
    public ResponseEntity<Event> create(@RequestBody Event event) {
        // Forzamos id=0 para que el repository le asigne uno nuevo.
        event.setId(0);
        Event saved = eventService.save(event);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);   // 201
    }

    // ─── UPDATE (reemplazo completo) ─────────────────────────────
    // PUT /api/events/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Event> replace(@PathVariable int id, @RequestBody Event event) {
        if (eventService.findById(id) == null) {
            return ResponseEntity.notFound().build();    // 404
        }
        event.setId(id);
        Event saved = eventService.save(event);
        return ResponseEntity.ok(saved);
    }

    // ─── PATCH (actualizacion parcial) ───────────────────────────
    // PATCH /api/events/{id}
    @PatchMapping("/{id}")
    public ResponseEntity<Event> patch(@PathVariable int id, @RequestBody Event partial) {
        Event updated = eventService.patch(id, partial);
        if (updated == null) {
            return ResponseEntity.notFound().build();    // 404
        }
        return ResponseEntity.ok(updated);
    }

    // ─── DELETE ──────────────────────────────────────────────────
    // DELETE /api/events/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        boolean deleted = eventService.deleteById(id);
        if (!deleted) {
            return ResponseEntity.notFound().build();    // 404
        }
        return ResponseEntity.noContent().build();       // 204
    }
}