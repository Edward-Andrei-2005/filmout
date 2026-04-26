package group02.filmout.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import group02.filmout.entity.Cinema;
import group02.filmout.repository.CinemaRepository;

@Service
public class CinemaService {

    @Autowired
    private CinemaRepository cinemaRepository;

    public Cinema save(Cinema cinema) {
        return cinemaRepository.save(cinema);
    }

    public List<Cinema> findAll() {
        return cinemaRepository.findAll();
    }

    public Cinema findById(int id) {
        return cinemaRepository.findById(id).orElse(null);
    }

    public boolean deleteById(int id) {
        if (cinemaRepository.existsById(id)) {
            cinemaRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public boolean existsAny() {
        return cinemaRepository.count() > 0;
    }

    public Cinema patch(int id, Cinema updatedFields) {
        Cinema existingCinema = findById(id);
        if (existingCinema == null) return null;
        if (updatedFields.getName() != null) {
            existingCinema.setName(updatedFields.getName());
        }
        if (updatedFields.getLatitude() != null) {
            existingCinema.setLatitude(updatedFields.getLatitude());
        }
        if (updatedFields.getLongitude() != null) {
            existingCinema.setLongitude(updatedFields.getLongitude());
        }
        return cinemaRepository.save(existingCinema);
    }
}
