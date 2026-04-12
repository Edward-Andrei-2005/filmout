package group02.filmout.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Event {
    // Attributes
    private int id, maxAttendees;
    private User admin;
    private Movie movie;
    private LocalDateTime date;
    private String location, description;
    private ArrayList<User> ListAttendees;
    private boolean isFull;

    // Constructor
    public Event(int id, int maxAttendees, User admin, Movie movie, LocalDateTime date, 
        String location, String description) {
        this.id = id;
        this.maxAttendees = maxAttendees;
        this.admin = admin;
        this.movie = movie;
        this.date = date;
        this.location = location;
        this.description = description;
        this.ListAttendees = new ArrayList<>();
        this.isFull = false; // Min number of attendees must be 2

        // Add the admin to the attendees list
    }

    // Methods
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getMaxAttendees() {
        return maxAttendees;
    }
    public void setMaxAttendees(int maxAttendees) {
        this.maxAttendees = maxAttendees;
    }
    public User getAdmin() {
        return admin;
    }
    public void setAdmin(User admin) {
        this.admin = admin;
    }
    public Movie getMovie() {
        return movie;
    }
    public void setMovie(Movie movie) {
        this.movie = movie;
    }
    public LocalDateTime getDate() {
        return date;
    }
    public void setDate(LocalDateTime date) {
        this.date = date;
    }
    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public ArrayList<User> getListAttendees() {
        return ListAttendees;
    }
    public void setListAttendees(ArrayList<User> listAttendees) {
        ListAttendees = listAttendees;
    }
    public boolean isFull() {
        return isFull;
    }
    public void setFull(boolean full) {
        isFull = full;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", maxAttendees=" + maxAttendees +
                ", admin=" + admin +
                ", movie=" + movie +
                ", date=" + date +
                ", location='" + location + '\'' +
                ", description='" + description + '\'' +
                ", ListAttendees=" + ListAttendees +
                ", isFull=" + isFull +
                 '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Event event = (Event) o;
        return id == event.id;
    }
}
