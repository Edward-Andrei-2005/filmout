package group02.filmout.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import group02.filmout.entity.Event;
import group02.filmout.entity.User;
import group02.filmout.repository.EventRepository;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    public Event save(Event event) {
        return eventRepository.save(event);
    }

    public List<Event> findAll() {
        return eventRepository.findAll();
    }

    public Event findById(int id) {
        return eventRepository.findById(id).orElse(null);
    }

    public boolean deleteById(int id) {
        if (eventRepository.existsById(id)) {
            eventRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<Event> findByAdmin(User user) {
        return eventRepository.findByAdmin(user);
    }

    public Event patch(int id, Event updatedFields) {
        Event existingEvent = findById(id);
        if (existingEvent == null) return null;
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
        existingEvent.setFull(updatedFields.isFull());
        return eventRepository.save(existingEvent);
    }
}
