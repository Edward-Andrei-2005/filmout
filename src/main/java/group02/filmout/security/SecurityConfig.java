package group02.filmout.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder; // Nuevo import

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() { // Devolvemos la interfaz
        return new BCryptPasswordEncoder();
    }
}