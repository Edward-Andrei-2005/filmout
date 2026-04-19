package group02.filmout.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import group02.filmout.entity.Movie;
import group02.filmout.entity.Review;
import group02.filmout.entity.User;
import group02.filmout.service.CinemaService;
import group02.filmout.service.MovieService;
import group02.filmout.service.ReviewService;
import jakarta.servlet.http.HttpSession;

@Controller
public class MainController {

  @Autowired
  private MovieService movieService;

  @Autowired
  private ReviewService reviewService;

  @Autowired
  private CinemaService cinemaService;

  @GetMapping("/")
  public String root() {
    return "redirect:/home";
  }

  @GetMapping("/home")
  public String home(HttpSession session, Model model) {
    User loggedUser = (User) session.getAttribute("loggedUser");
    if (loggedUser != null) {
      model.addAttribute("loggedUser", loggedUser);
    }

    List<Movie> movies = movieService.getUpcomingMovies();
    for (Movie movie : movies) {
      List<Review> raw = reviewService.findByMovieApiId(movie.getId());
      List<Map<String, Object>> views = new ArrayList<>();
      for (Review r : raw) {
        Map<String, Object> v = new HashMap<>();
        v.put("id", r.getId());
        v.put("userName", r.getUser().getUserName());
        v.put("grade", String.format("%.0f", r.getGrade()));
        v.put("comment", r.getComment() != null ? r.getComment() : "");
        v.put("hasComment", r.getComment() != null && !r.getComment().isBlank());
        v.put("isOwner", loggedUser != null && r.getUser().getId() == loggedUser.getId());
        v.put("movieApiId", movie.getId());
        views.add(v);
      }
      movie.setReviews(views);
    }

    model.addAttribute("movies", movies);
    return "index";
  }

  @GetMapping("/map")
  public String map(HttpSession session, Model model) {
    User loggedUser = (User) session.getAttribute("loggedUser");
    if (loggedUser != null) model.addAttribute("loggedUser", loggedUser);
    model.addAttribute("cinemas", cinemaService.findAll());
    model.addAttribute("movies", movieService.getUpcomingMovies());
    return "map";
  }
}
