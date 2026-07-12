package com.example.Clase1.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuracion de CORS (Cross-Origin Resource Sharing).
 *
 * Por seguridad, el navegador bloquea que una pagina servida desde un
 * "origen" (ej. http://localhost:5173, el frontend) haga peticiones a
 * otro origen distinto (http://localhost:8080, este backend).
 *
 * Aqui le decimos a Spring: "confia en el frontend y dejalo llamar a la API".
 *
 * @Configuration = esta clase aporta configuracion a Spring al arrancar.
 * WebMvcConfigurer = interfaz para personalizar la parte web de Spring MVC.
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry
                // A que rutas aplica: todas las de la API.
                .addMapping("/api/**")
                // Que origen (frontend) tiene permiso. Es el puerto de Vite.
                .allowedOrigins("http://localhost:5173")
                // Que verbos HTTP se permiten desde el frontend.
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                // Que cabeceras se permiten (todas, ej. Content-Type).
                .allowedHeaders("*");
    }
}
