package group02.filmout.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import group02.filmout.entity.Event;
import group02.filmout.entity.User;
import group02.filmout.service.CinemaService;
import group02.filmout.service.EventService;
import jakarta.servlet.http.HttpSession;

@Controller
public class EventController {

    @Autowired
    private EventService eventService;

    @Autowired
    private CinemaService cinemaService;

    @GetMapping("/events")
    public String events(HttpSession session, Model model) {
        User loggedUser = (User) session.getAttribute("loggedUser");
        if (loggedUser != null) {
            model.addAttribute("loggedUser", loggedUser);
        }
        return "Event/list";
    }

    @GetMapping("/events/new")
    public String newEventForm(HttpSession session, Model model) {
        User loggedUser = (User) session.getAttribute("loggedUser");
        if (loggedUser == null) {
            return "redirect:/login";
        }
        model.addAttribute("loggedUser", loggedUser);
        model.addAttribute("cinemas", cinemaService.findAll());
        return "Event/form";
    }

    // ESTO SE VA A AÑADIR DESDE EL INDEX.HTML
    @PostMapping("/events/new")
    public String newEventPost(@RequestParam String movie, @RequestParam String description, @RequestParam String date, @RequestParam int maxAttendees, @RequestParam String location, HttpSession session, Model model) {
        User loggedUser = (User) session.getAttribute("loggedUser");
        if (loggedUser == null) {
            return "redirect:/login";
        }

        if (maxAttendees < 2) {
            model.addAttribute("error", "El aforo mínimo es 2 personas.");
            model.addAttribute("loggedUser", loggedUser);
            model.addAttribute("cinemas", cinemaService.findAll());
            return "Event/form";
        }

        Event event = new Event();
        event.setAdmin(loggedUser);
        event.setDescription(description);
        event.setDate(LocalDateTime.parse(date));
        event.setMaxAttendees(maxAttendees);
        event.setLocation(location);
        event.setListAttendees(new ArrayList<>());
        event.setFull(false);

        eventService.save(event);
        return "redirect:/events";
    }
}
