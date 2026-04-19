package group02.filmout.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import group02.filmout.entity.Review;
import group02.filmout.entity.User;
import group02.filmout.service.ReviewService;
import jakarta.servlet.http.HttpSession;

@Controller
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping("/reviews/new")
    public String create(@RequestParam int movieApiId, @RequestParam float grade, @RequestParam(required = false, defaultValue = "") String comment,
        HttpSession session) {
        User loggedUser = (User) session.getAttribute("loggedUser");
        if (loggedUser == null) return "redirect:/login";

        Review review = new Review();
        review.setUser(loggedUser);
        review.setMovieApiId(movieApiId);
        review.setGrade(grade);
        review.setComment(comment);
        reviewService.save(review);

        return "redirect:/home?openModal=" + movieApiId;
    }

    @PostMapping("/reviews/{id}/edit")
    public String edit(@PathVariable int id, @RequestParam int movieApiId, @RequestParam float grade, @RequestParam(required = false, defaultValue = "") String comment,
        HttpSession session) {
        User loggedUser = (User) session.getAttribute("loggedUser");
        if (loggedUser == null) return "redirect:/login";

        Review existing = reviewService.findById(id);
        if (existing == null || existing.getUser().getId() != loggedUser.getId()) {
            return "redirect:/home";
        }

        Review partial = new Review();
        partial.setGrade(grade);
        partial.setComment(comment);
        reviewService.patch(id, partial);

        return "redirect:/home?openModal=" + movieApiId;
    }

    @PostMapping("/reviews/{id}/delete")
    public String delete(@PathVariable int id, @RequestParam int movieApiId, HttpSession session) {
        User loggedUser = (User) session.getAttribute("loggedUser");
        if (loggedUser == null) return "redirect:/login";

        Review existing = reviewService.findById(id);
        if (existing != null && existing.getUser().getId() == loggedUser.getId()) {
            reviewService.deleteById(id);
        }

        return "redirect:/home?openModal=" + movieApiId;
    }
}
