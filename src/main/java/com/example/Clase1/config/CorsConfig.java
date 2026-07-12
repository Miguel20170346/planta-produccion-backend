package com.example.Clase1.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuracion de CORS (Cross-Origin Resource Sharing).
 *
 * Por seguridad, el navegador bloquea que una pagina servida desde un
 * "origen" (ej. el frontend) haga peticiones a otro origen distinto
 * (este backend). Aqui le decimos a Spring cual frontend tiene permiso.
 *
 * El/los origenes permitidos se configuran con la propiedad
 * app.cors.allowed-origins (separados por coma). Por defecto, el puerto de
 * Vite local; en produccion se define con la URL del frontend desplegado.
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Value("${app.cors.allowed-origins:http://localhost:5173}")
    private String[] allowedOrigins;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry
                .addMapping("/api/**")
                .allowedOrigins(allowedOrigins)
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders("*");
    }
}
