package group02.filmout.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import group02.filmout.entity.User;
import group02.filmout.service.UserService;
import jakarta.servlet.http.HttpSession;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/profile")
    public String profile(HttpSession session, Model model) {
        User loggedUser = (User) session.getAttribute("loggedUser");
        if (loggedUser == null) {
            return "redirect:/login";
        }
        model.addAttribute("loggedUser", loggedUser);
        return "User/form";
    }

    @PostMapping("/profile/delete")
    public String deleteProfile(HttpSession session) {
        User loggedUser = (User) session.getAttribute("loggedUser");
        if (loggedUser == null) return "redirect:/login";
        userService.deleteById(loggedUser.getId());
        session.invalidate();
        return "redirect:/login";
    }

    @PostMapping("/profile")
    public String profilePost(@RequestParam String username, @RequestParam String email, @RequestParam String password, HttpSession session, Model model) {
        User loggedUser = (User) session.getAttribute("loggedUser");
        if (loggedUser == null) {
            return "redirect:/login";
        }

        User byUsername = userService.findByUserName(username);
        if (byUsername != null && byUsername.getId() != loggedUser.getId()) {
            model.addAttribute("error", "Username already taken.");
            model.addAttribute("loggedUser", loggedUser);
            return "User/form";
        }

        User byEmail = userService.findByEmail(email);
        if (byEmail != null && byEmail.getId() != loggedUser.getId()) {
            model.addAttribute("error", "Email already registered.");
            model.addAttribute("loggedUser", loggedUser);
            return "User/form";
        }

        User partial = new User();
        partial.setUserName(username);
        partial.setEmail(email);
        if (password != null && !password.isBlank()) {
            partial.setPassword(password);
        }

        User saved = userService.patch(loggedUser.getId(), partial);
        session.setAttribute("loggedUser", saved);
        model.addAttribute("loggedUser", saved);
        model.addAttribute("success", "Profile updated successfully.");
        return "User/form";
    }
}
