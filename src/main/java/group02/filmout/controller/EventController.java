package group02.filmout.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import group02.filmout.entity.Event;
import group02.filmout.entity.Movie;
import group02.filmout.entity.User;
import group02.filmout.service.CinemaService;
import group02.filmout.service.EventService;
import group02.filmout.service.MovieService;
import jakarta.servlet.http.HttpSession;

@Controller
public class EventController {

    private final EventService eventService;
    private final CinemaService cinemaService;
    private final MovieService movieService;

    public EventController(EventService eventService, CinemaService cinemaService, MovieService movieService) {
        this.eventService = eventService;
        this.cinemaService = cinemaService;
        this.movieService = movieService;
    }

    @GetMapping("/events")
    public String events(HttpSession session, Model model) {
        User loggedUser = (User) session.getAttribute("loggedUser");
        List<Event> allEvents = eventService.findAll();
        allEvents.forEach(this::loadMovie);

        if (loggedUser != null) {
            model.addAttribute("loggedUser", loggedUser);
            List<Event> myEvents = new ArrayList<>();
            List<Event> joinedEvents = new ArrayList<>();
            List<Event> openEvents = new ArrayList<>();
            for (Event e : allEvents) {
                // SEGURO CONTRA NULOS APLICADO AQUÍ
                if (e.getAdmin() != null && e.getAdmin().getId() == loggedUser.getId()) {
                    myEvents.add(e);
                } else if (e.getListAttendees() != null &&
                        e.getListAttendees().stream().anyMatch(u -> u.getId() == loggedUser.getId())) {
                    joinedEvents.add(e);
                } else {
                    openEvents.add(e);
                }
            }
            model.addAttribute("myEvents", myEvents);
            model.addAttribute("joinedEvents", joinedEvents);
            model.addAttribute("openEvents", openEvents);
        } else {
            model.addAttribute("openEvents", allEvents);
        }
        return "event/list";
    }

    @GetMapping("/events/new")
    public String newEventForm(@RequestParam(required = false) Integer movieId, HttpSession session, Model model) {
        User loggedUser = (User) session.getAttribute("loggedUser");
        if (loggedUser == null) {
            return "redirect:/login";
        }
        model.addAttribute("loggedUser", loggedUser);
        model.addAttribute("cinemas", cinemaService.findAll());
        if (movieId != null) {
            Movie movie = findMovieByApiId(movieId);
            if (movie != null) {
                model.addAttribute("movie", movie);
            }
        }
        return "event/form";
    }

    @PostMapping("/events/new")
    public String newEventPost(@RequestParam(required = false) Integer movieId, @RequestParam String description, @RequestParam String date, @RequestParam int maxAttendees,
                               @RequestParam String location, HttpSession session, Model model) {
        User loggedUser = (User) session.getAttribute("loggedUser");
        if (loggedUser == null) {
            return "redirect:/login";
        }

        Movie movie = movieId != null ? findMovieByApiId(movieId) : null;

        if (maxAttendees < 2 || maxAttendees > 50) {
            model.addAttribute("error", "Capacity must be between 2 and 50 people.");
            model.addAttribute("loggedUser", loggedUser);
            model.addAttribute("cinemas", cinemaService.findAll());
            if (movie != null) model.addAttribute("movie", movie);
            return "event/form";
        }

        eventService.createEvent(
                loggedUser.getId(), movie, movieId != null ? movieId : 0,
                location, LocalDateTime.parse(date), description, maxAttendees);
        return "redirect:/events";
    }

    @GetMapping("/events/{id}")
    public String eventDetail(@PathVariable int id, HttpSession session, Model model) {
        User loggedUser = (User) session.getAttribute("loggedUser");
        if (loggedUser == null) return "redirect:/login";

        Event event = eventService.findById(id);
        if (event == null) return "redirect:/events";
        loadMovie(event);

        boolean isAdmin = event.getAdmin() != null && event.getAdmin().getId() == loggedUser.getId();
        boolean isJoined = event.getListAttendees() != null &&
                event.getListAttendees().stream().anyMatch(u -> u.getId() == loggedUser.getId());

        model.addAttribute("loggedUser", loggedUser);
        model.addAttribute("event", event);
        model.addAttribute("eventId", id);
        model.addAttribute("isAdmin", isAdmin);
        model.addAttribute("canJoin", !isAdmin && !isJoined && !event.isFull());
        model.addAttribute("canLeave", !isAdmin && isJoined);
        return "event/detail";
    }

    @PostMapping("/events/{id}/delete")
    public String deleteEvent(@PathVariable int id, HttpSession session) {
        User loggedUser = (User) session.getAttribute("loggedUser");
        if (loggedUser == null) return "redirect:/login";

        Event event = eventService.findById(id);
        // Se aplica seguro contra nulos
        if (event != null && event.getAdmin() != null && event.getAdmin().getId() == loggedUser.getId()) {
            eventService.deleteById(id);
        }
        return "redirect:/events";
    }

    @PostMapping("/events/{id}/join")
    public String joinEvent(@PathVariable int id, HttpSession session) {
        User loggedUser = (User) session.getAttribute("loggedUser");
        if (loggedUser == null) return "redirect:/login";

        eventService.addAttendee(id, loggedUser.getId());
        return "redirect:/events/" + id;
    }

    @PostMapping("/events/{id}/leave")
    public String leaveEvent(@PathVariable int id, HttpSession session) {
        User loggedUser = (User) session.getAttribute("loggedUser");
        if (loggedUser == null) return "redirect:/login";

        eventService.removeAttendee(id, loggedUser.getId());
        return "redirect:/events/" + id;
    }

    @GetMapping("/events/{id}/edit")
    public String editEventForm(@PathVariable int id, HttpSession session, Model model) {
        User loggedUser = (User) session.getAttribute("loggedUser");
        if (loggedUser == null) return "redirect:/login";

        Event event = eventService.findById(id);
        if (event == null) return "redirect:/events";

        model.addAttribute("loggedUser", loggedUser);
        model.addAttribute("event", event);
        model.addAttribute("cinemas", buildCinemaOptions(event.getLocation()));
        return "event/edit";
    }

    @PostMapping("/events/{id}/edit")
    public String editEventPost(@PathVariable int id, @RequestParam String description, @RequestParam String date,
                                @RequestParam int maxAttendees, @RequestParam String location, HttpSession session, Model model) {
        User loggedUser = (User) session.getAttribute("loggedUser");
        if (loggedUser == null) return "redirect:/login";

        Event event = eventService.findById(id);
        if (event == null) return "redirect:/events";

        if (maxAttendees < 2 || maxAttendees > 50) {
            model.addAttribute("loggedUser", loggedUser);
            model.addAttribute("event", event);
            model.addAttribute("error", "Capacity must be between 2 and 50 people.");
            model.addAttribute("cinemas", buildCinemaOptions(event.getLocation()));
            return "event/edit";
        }

        event.setDescription(description);
        event.setDate(java.time.LocalDateTime.parse(date));
        event.setMaxAttendees(maxAttendees);
        event.setLocation(location);
        eventService.save(event);
        return "redirect:/events";
    }

    private List<Map<String, Object>> buildCinemaOptions(String currentLocation) {
        return cinemaService.findAll().stream().map(c -> {
            Map<String, Object> entry = new HashMap<>();
            entry.put("name", c.getName());
            entry.put("selected", c.getName().equals(currentLocation));
            return entry;
        }).collect(Collectors.toList());
    }

    private Movie findMovieByApiId(int apiId) {
        return movieService.getUpcomingMovies().stream()
                .filter(m -> m.getApiId() == apiId)
                .findFirst().orElse(null);
    }

    private void loadMovie(Event event) {
        if (event.getMovieApiId() != 0) {
            event.setMovie(findMovieByApiId(event.getMovieApiId()));
        }
    }
}