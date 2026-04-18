package group02.filmout.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import group02.filmout.entity.User;
import jakarta.servlet.http.HttpSession;

@Controller
public class UserController {

    @GetMapping("/profile")
    public String profile(HttpSession session, Model model) {
        User loggedUser = (User) session.getAttribute("loggedUser");
        if (loggedUser == null) {
            return "redirect:/login";
        }
        model.addAttribute("loggedUser", loggedUser);
        return "User/form";
    }
}
