package group02.filmout.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "events")
public class Event {
    // Attributes
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int maxAttendees;

    @ManyToOne
    @JoinColumn(name = "admin_id")
    private User admin;

    @Transient
    private Movie movie;

    private int movieApiId;

    private LocalDateTime date;
    private String location, description;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "event_attendees",
        joinColumns = @JoinColumn(name = "event_id"),
        inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> listAttendees = new ArrayList<>();

    private boolean isFull;

    // Constructor
    public Event() {};
    public Event(int maxAttendees, User admin, Movie movie, LocalDateTime date,
        String location, String description) {
        this.maxAttendees = maxAttendees;
        this.admin = admin;
        this.movie = movie;
        this.movieApiId = movie != null ? movie.getId() : 0;
        this.date = date;
        this.location = location;
        this.description = description;
        this.listAttendees = new ArrayList<>();
        this.isFull = false;
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
        if (movie != null) this.movieApiId = movie.getId();
    }
    public int getMovieApiId() {
        return movieApiId;
    }
    public void setMovieApiId(int movieApiId) {
        this.movieApiId = movieApiId;
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
    public List<User> getListAttendees() {
        return listAttendees;
    }
    public void setListAttendees(List<User> listAttendees) {
        this.listAttendees = listAttendees;
    }
    public int getAttendeeCount() {
        return listAttendees == null ? 0 : listAttendees.size();
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
                ", movieApiId=" + movieApiId +
                ", date=" + date +
                ", location='" + location + '\'' +
                ", description='" + description + '\'' +
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
