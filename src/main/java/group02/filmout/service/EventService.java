package group02.filmout.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import group02.filmout.entity.Event;
import group02.filmout.entity.Movie;
import group02.filmout.entity.User;
import group02.filmout.repository.EventRepository;
import group02.filmout.repository.UserRepository;

@Service
public class EventService {

    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    public EventService(EventRepository eventRepository, UserRepository userRepository) {
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
    }

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


    @Transactional
    public Event createEvent(int adminId, Movie movie, int movieApiId,
                             String location, LocalDateTime date, String description, int maxAttendees) {
        User admin = userRepository.findById(adminId).orElse(null);
        if (admin == null) return null;

        Event event = new Event();
        event.setAdmin(admin);
        event.setMovie(movie);
        event.setMovieApiId(movieApiId);
        event.setLocation(location);
        event.setDate(date);
        event.setDescription(description);
        event.setMaxAttendees(maxAttendees);
        event.getListAttendees().add(admin);

        // Eliminado setFull() porque ahora se calcula automáticamente
        return eventRepository.save(event);
    }

    @Transactional
    public Event addAttendee(int eventId, int userId) {
        Event event = eventRepository.findById(eventId).orElse(null);
        if (event == null || event.isFull()) return event;
        if (event.getListAttendees().stream().anyMatch(u -> u.getId() == userId)) return event;

        User user = userRepository.findById(userId).orElse(null);
        if (user == null) return event;

        event.getListAttendees().add(user);
        // Eliminado setFull() porque isFull() ya devolverá true si se alcanza el máximo
        return eventRepository.save(event);
    }

    @Transactional
    public Event removeAttendee(int eventId, int userId) {
        Event event = eventRepository.findById(eventId).orElse(null);
        if (event == null) return null;

        event.getListAttendees().removeIf(u -> u.getId() == userId);
        return eventRepository.save(event);
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

        return eventRepository.save(existingEvent);
    }
}