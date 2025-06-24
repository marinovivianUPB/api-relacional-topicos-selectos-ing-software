package com.topicossoftware.marino.upb.edu.api;

import com.auth0.jwt.JWTVerifier;
import com.topicossoftware.marino.upb.edu.api.api.utils.JWTFilter;
import com.topicossoftware.marino.upb.edu.api.domain.interfaces.app.AuthService;
import com.topicossoftware.marino.upb.edu.api.domain.interfaces.app.CompradorService;
import com.topicossoftware.marino.upb.edu.api.domain.interfaces.app.UsuarioService;
import jakarta.servlet.Filter;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuarios")
class SecuredController {
    @GetMapping("/test")
    public String securedEndpoint() {
        return "Authorized access";
    }
}

@TestConfiguration
public class TestConfig {

    @Bean
    public JWTVerifier jwtVerifier() {
        return Mockito.mock(JWTVerifier.class); // manual mock
    }

    @Bean(name = "testJwtFilter")
    public Filter testJwtFilter() {
        return (request, response, chain) -> chain.doFilter(request, response);
    }
    @Bean(name = "jwtFilter")
    public Filter jwtFilter(JWTVerifier jwtVerifier) {
        JWTFilter filter = new JWTFilter();
        filter.setVerifier(jwtVerifier);
        return filter;
    }

    @Bean
    public SecuredController securedController() {
        return new SecuredController();
    }

    @Bean
    public UsuarioService usuarioService() {
        return Mockito.mock(UsuarioService.class);
    }

    @Bean
    public CompradorService compradorService() {
        return Mockito.mock(CompradorService.class);
    }
    @Bean
    public AuthService authService() {
        return Mockito.mock(AuthService.class);
    }

}
