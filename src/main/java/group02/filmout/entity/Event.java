package group02.filmout.entity;

import java.time.LocalDate;

public class Event {
    private String movieTitle;
    private LocalDate date;
    private String cinemaLocation;
    private int numberOfPersons;

    public Event(String movieTitle, LocalDate date, String cinemaLocation) {
        this.movieTitle = movieTitle;
        this.date = date;
        this.cinemaLocation = cinemaLocation;
        numberOfPersons = 1;
    }
}
