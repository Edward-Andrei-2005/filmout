package group02.filmout.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Repository;

import group02.filmout.entity.Cinema;

@Repository
public class CinemaRepository {

  // Attributes
  private HashMap<Integer, Cinema> mapCinemas = new HashMap<>();
  private AtomicInteger nextId = new AtomicInteger(1);

  // Constructor
  public CinemaRepository() {};

  // Methods
  public Cinema save(Cinema cinema) { 
    if (cinema.getId() == 0) { 
      cinema.setId(nextId.getAndIncrement()); 
    } 
    mapCinemas.put(cinema.getId(), cinema); return cinema; }

  public List<Cinema> findAll() {
    return new ArrayList<>(mapCinemas.values());
  }

  public Cinema findById(int id) {
    return mapCinemas.get(id);
  }

  public Cinema findByName(String name) {
    for (Cinema c : mapCinemas.values()) {
      if (c.getName().equalsIgnoreCase(name)) {
        return c;
      }
    }
    return null;
  }

  /*public boolean saveCinema(Cinema cinema) {
    if (mapCinemas.containsKey(cinema.getId())) return false;

    mapCinemas.put(cinema.getId(), cinema);
    return true;
  }*/

  /*public boolean deleteCinema(Cinema cinema) {
    return mapCinemas.remove(cinema.getId()) != null;
  }*/
 public boolean deleteCinema(int id) {
    return mapCinemas.remove(id) != null;
  }
}