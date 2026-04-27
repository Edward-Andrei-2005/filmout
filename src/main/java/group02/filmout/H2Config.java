package group02.filmout; // OJO: Asegúrate de que este es tu paquete

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class H2Config {

    @Bean
    public ServletRegistrationBean<org.h2.server.web.JakartaWebServlet> h2servletRegistration() {
        // Usamos la versión "Jakarta" adaptada a tu versión moderna de Spring Boot
        ServletRegistrationBean<org.h2.server.web.JakartaWebServlet> registrationBean =
                new ServletRegistrationBean<>(new org.h2.server.web.JakartaWebServlet());

        registrationBean.addUrlMappings("/h2-console/*");

        return registrationBean;
    }
}