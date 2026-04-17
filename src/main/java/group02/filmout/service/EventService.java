package group02.filmout.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import group02.filmout.entity.Event;
import group02.filmout.repository.EventRepository;

@Service
public class EventService {
    //Attributes
    @Autowired
    private EventRepository eventRepository;

    // CRUD
    public Event save(Event event) {
        return eventRepository.save(event);
    }

    public List<Event> findAll() {
        return eventRepository.findAll();
    }

    public Event findById(int id) {
        return eventRepository.findById(id);
    }

    public boolean deleteById(int id) {
        return eventRepository.deleteEvent(id);
    }

    //PATCH
    public Event patch(int id, Event updatedFields) {
        Event existingEvent = eventRepository.findById(id);
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
        return eventRepository.save(existingEvent);
    }
}