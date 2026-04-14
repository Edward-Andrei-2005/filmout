package group02.filmout.service;

import group02.filmout.entity.Event;
import org.springframework.stereotype.Service;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class EventService {

    private Map<Integer, Event> events = new ConcurrentHashMap<>();
    private AtomicInteger nextId = new AtomicInteger(1);

    // CRUD


    public Event save(Event event) {
        if (event.getId() == 0) {
            event.setId(nextId.getAndIncrement());
        }
        events.put(event.getId(), event);
        return event;
    }

    public Collection<Event> findAll() {
        return events.values();
    }

    public Event findById(int id) {
        return events.get(id);
    }

    public Event deleteById(int id) {
        return events.remove(id);
    }

    //PATCH
    public Event patch(int id, Event updatedFields) {
        Event existingEvent = events.get(id);
        if (existingEvent != null) {
            if (updatedFields.getMaxAttendees() != 0) {
                existingEvent.setMaxAttendees(updatedFields.getMaxAttendees());
            }
            if (updatedFields.getDate() != null) {
                existingEvent.setDate(updatedFields.getDate());
            }
            if (updatedFields.getLocation() != null) {
                existingEvent.setLocation(updatedFields.getLocation());
            }
            if (updatedFields.getDescription() != null) {
                existingEvent.setDescription(updatedFields.getDescription());
            }
            // Al ser un booleano primitivo, se actualiza el estado actual
            existingEvent.setFull(updatedFields.isFull());
        }
        return existingEvent;
    }
}