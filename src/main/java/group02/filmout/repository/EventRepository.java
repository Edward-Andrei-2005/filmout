package group02.filmout.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Repository;

import group02.filmout.entity.Event;
import group02.filmout.entity.Movie;
import group02.filmout.entity.User;

@Repository
public class EventRepository {
  // Attributes
  private HashMap<Integer, Event> mapEvents;
  private AtomicInteger nextId = new AtomicInteger(1);
  
  // Constructor
  public EventRepository() {}

  // Methods
  public Event save(Event event) { 
    if (event.getId() == 0) { 
      event.setId(nextId.getAndIncrement()); 
    } 
    mapEvents.put(event.getId(), event); return event; 
  }

  public List<Event> findAll() {
    return new ArrayList<>(mapEvents.values());
  }

  public Event findById(int id) {
    return mapEvents.get(id);
  }

  public List<Event> findByUser(User user) {
    ArrayList<Event> auxEvents = new ArrayList<>();

    for (Event e: mapEvents.values()) {
      if (e.getAdmin().equals(user)) {
        auxEvents.add(e);
      }
    }

    return auxEvents;
  }

  public List<Event> findByMovie(Movie movie) {
    ArrayList<Event> auxEvents = new ArrayList<>();

    for (Event e: mapEvents.values()) {
      if (e.getMovie().equals(movie)) {
        auxEvents.add(e);
      }
    }

    return auxEvents;
  }

  public boolean saveEvent(Event event) {
    if (mapEvents.containsKey(event.getId())) return false;

    mapEvents.put(event.getId(), event);
    return true;
  }

  public boolean deleteEvent(Event event) {
    return mapEvents.remove(event.getId()) != null;
  }
}