package group02.filmout.service;

import group02.filmout.entity.Cinema;
import org.springframework.stereotype.Service;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class CinemaService {

    private Map<Integer, Cinema> cinemas = new ConcurrentHashMap<>();
    private AtomicInteger nextId = new AtomicInteger(1);

    // CRUD


    public Cinema save(Cinema cinema) {
        if (cinema.getId() == 0) {
            cinema.setId(nextId.getAndIncrement());
        }
        cinemas.put(cinema.getId(), cinema);
        return cinema;
    }

    public Collection<Cinema> findAll() {
        return cinemas.values();
    }

    public Cinema findById(int id) {
        return cinemas.get(id);
    }

    public Cinema deleteById(int id) {
        return cinemas.remove(id);
    }

    //PATCH
    public Cinema patch(int id, Cinema updatedFields) {
        Cinema existingCinema = cinemas.get(id);
        if (existingCinema != null) {
            if (updatedFields.getName() != null) {
                existingCinema.setName(updatedFields.getName());
            }
            if (updatedFields.getLatitude() != null) {
                existingCinema.setLatitude(updatedFields.getLatitude());
            }
            if (updatedFields.getLongitude() != null) {
                existingCinema.setLongitude(updatedFields.getLongitude());
            }
        }
        return existingCinema;
    }
}