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
public class AuthController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String login(@RequestParam(required = false) String registered, Model model) {
        if (registered != null) {
            model.addAttribute("success", "¡Cuenta creada! Ya puedes iniciar sesión.");
        }
        return "login";
    }

    @PostMapping("/login")
    public String loginPost(@RequestParam String username, @RequestParam String password, HttpSession session, Model model) {
        User user = userService.findByUserName(username);
        if (user == null || !user.getPassword().equals(password)) {
            model.addAttribute("error", "Usuario o contraseña incorrectos.");
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
            model.addAttribute("error", "Las contraseñas no coinciden.");
            return "register";
        }
        if (userService.findByUserName(username) != null) {
            model.addAttribute("error", "El nombre de usuario ya está en uso.");
            return "register";
        }
        if (userService.findByEmail(email) != null) {
            model.addAttribute("error", "El email ya está registrado.");
            return "register";
        }
        userService.save(new User(username, password, email));
        return "redirect:/login?registered";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
