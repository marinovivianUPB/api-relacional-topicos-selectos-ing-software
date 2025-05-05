package com.topicossoftware.marino.upb.edu.api.app.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.topicossoftware.marino.upb.edu.api.app.exceptions.UsuarioServiceException;
import com.topicossoftware.marino.upb.edu.api.domain.entity.Usuario;
import com.topicossoftware.marino.upb.edu.api.domain.interfaces.app.AuthService;
import com.topicossoftware.marino.upb.edu.api.domain.interfaces.infrastructure.UsuarioRepository;
import com.topicossoftware.marino.upb.edu.api.domain.model.LoginRequest;
import com.topicossoftware.marino.upb.edu.api.domain.model.LoginResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;

@Service
@Log4j2
public class AuthServiceImpl implements AuthService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private Algorithm algorithm;

    @Autowired
    private JWTVerifier verifier;

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        log.info("Verificando intento de Login de usuario con email:" + loginRequest.getEmail());
        Usuario usuario = usuarioRepository.findByEmail(loginRequest.getEmail());
        if(!passwordEncoder.matches(loginRequest.getPassword(),usuario.getPassword())){
            log.info("Contraseña Incorrecta");
            throw new UsuarioServiceException("Contraseña o Email Incorrectos", "404");
        }
        LoginResponse loginResponse = new LoginResponse();
        try{
            String token = JWT.create()
                    .withExpiresAt(Date.from(Instant.now().plusSeconds(3600)))
                    .sign(algorithm);
            loginResponse.setToken(token);
            loginResponse.setUsuarioId(usuario.getUsuarioId());
        } catch (JWTCreationException exception) {
            log.info("Hubo un problema al generar el token: " + exception.getMessage());
            throw new UsuarioServiceException("Contraseña o Email Incorrectos", "404");
        }
        return loginResponse;
    }

}