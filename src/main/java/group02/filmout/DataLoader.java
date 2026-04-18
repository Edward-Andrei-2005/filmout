package group02.filmout;

import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import group02.filmout.entity.Cinema;
import group02.filmout.service.CinemaService;
import jakarta.annotation.PostConstruct;

@Component
public class DataLoader {

    @Autowired
    private CinemaService cinemaService;

    @PostConstruct
    public void load() throws Exception {
        InputStream is = getClass().getResourceAsStream("/data/cinemas.json");
        List<Cinema> cinemas = new ObjectMapper().readValue(is, new TypeReference<List<Cinema>>() {});
        cinemas.forEach(cinemaService::save);
    }
}
