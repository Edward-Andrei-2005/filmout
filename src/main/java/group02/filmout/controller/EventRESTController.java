package group02.filmout.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import group02.filmout.entity.Event;
import group02.filmout.service.EventService;

@RestController
@RequestMapping("/api/events")
public class EventRESTController {

    private final EventService eventService;

    public EventRESTController(EventService eventService) {
        this.eventService = eventService;
    }

    // GET /api/events
    @GetMapping
    public List<Event> getAll() {
        return eventService.findAll();
    }

    // GET /api/events/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Event> getOne(@PathVariable int id) {
        Event event = eventService.findById(id);
        if (event == null) {
            return ResponseEntity.notFound().build();   // 404
        }
        return ResponseEntity.ok(event);                // 200
    }

    // POST /api/events
    @PostMapping
    public ResponseEntity<Event> create(@RequestBody Event event) {
        // Forzamos id=0 para que el repository le asigne uno nuevo.
        event.setId(0);
        Event saved = eventService.save(event);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);   // 201
    }

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

    // PATCH /api/events/{id}
    @PatchMapping("/{id}")
    public ResponseEntity<Event> patch(@PathVariable int id, @RequestBody Event partial) {
        Event updated = eventService.patch(id, partial);
        if (updated == null) {
            return ResponseEntity.notFound().build();    // 404
        }
        return ResponseEntity.ok(updated);
    }

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