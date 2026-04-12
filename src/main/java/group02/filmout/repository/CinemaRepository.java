package group02.filmout.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import group02.filmout.entity.Cinema;

@Repository
public class CinemaRepository {

  // Attributes
  private HashMap<Integer, Cinema> mapCinemas;

  // Constructor
  public CinemaRepository() {};

  // Methods
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

  public boolean saveCinema(Cinema cinema) {
    if (mapCinemas.containsKey(cinema.getId())) return false;

    mapCinemas.put(cinema.getId(), cinema);
    return true;
  }

  public boolean deleteCinema(Cinema cinema) {
    return mapCinemas.remove(cinema.getId()) != null;
  }
}