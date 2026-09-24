package group02.filmout.controller;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import group02.filmout.entity.User;
import group02.filmout.service.UserService;
import jakarta.servlet.http.HttpSession;

@Controller
public class AuthController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/login")
    public String login(@RequestParam(required = false) String registered, Model model) {
        if (registered != null) {
            model.addAttribute("success", "Account created! You can now log in.");
        }
        return "login";
    }

    @PostMapping("/login")
    public String loginPost(@RequestParam String username, @RequestParam String password, HttpSession session, Model model) {
        User user = userService.findByUserName(username);

        if (user == null || !passwordEncoder.matches(password, user.getPassword())) {
            model.addAttribute("error", "Incorrect username or password.");
            return "login";
        }
        session.setAttribute("loggedUser", user);
        return "redirect:/home";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping("/register")
    public String registerPost(@RequestParam String username, @RequestParam String email, @RequestParam String password, @RequestParam String confirmPassword, Model model) {
        if (!password.equals(confirmPassword)) {
            model.addAttribute("error", "Passwords do not match.");
            return "register";
        }
        if (userService.findByUserName(username) != null) {
            model.addAttribute("error", "Username already taken.");
            return "register";
        }
        if (userService.findByEmail(email) != null) {
            model.addAttribute("error", "Email already registered.");
            return "register";
        }

        // El UserService.save() se encargará de encriptar la contraseña.
        User newUser = new User();
        newUser.setUserName(username);
        newUser.setEmail(email);
        newUser.setPassword(password);
        userService.save(newUser);

        return "redirect:/login?registered";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/home";
    }
}
