package com.topicossoftware.marino.upb.edu.api.app.services;

import com.topicossoftware.marino.upb.edu.api.app.exceptions.UsuarioServiceException;
import com.topicossoftware.marino.upb.edu.api.domain.entity.Rol;
import com.topicossoftware.marino.upb.edu.api.domain.entity.Usuario;
import com.topicossoftware.marino.upb.edu.api.domain.interfaces.app.UsuarioService;
import com.topicossoftware.marino.upb.edu.api.domain.interfaces.infrastructure.RolRepository;
import com.topicossoftware.marino.upb.edu.api.domain.interfaces.infrastructure.UsuarioRepository;
import com.topicossoftware.marino.upb.edu.api.domain.model.UpdatePasswordRequest;
import com.topicossoftware.marino.upb.edu.api.domain.model.UsuarioCompleteResponse;
import com.topicossoftware.marino.upb.edu.api.domain.model.UsuarioRequest;
import com.topicossoftware.marino.upb.edu.api.domain.model.UsuarioResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private RolRepository rolRepository;

    private UsuarioServiceException userDoesntExist(long usuarioId){
        UsuarioServiceException usuarioServiceCustomException = new UsuarioServiceException("No existe el usuario con id "+usuarioId, "400");

        return usuarioServiceCustomException;
    }
    @Override
    public long addUsuario(UsuarioRequest usuarioRequest) {
        log.info("Usuario Service: Creando Usuario:" + usuarioRequest.getEmail());

        Rol rol = rolRepository.findByNombre(usuarioRequest.getRol());

        String password = usuarioRequest.getNombres().replaceAll("\\s", "")+usuarioRequest.getCarnetIdentidad();

        Usuario usuario = Usuario.builder()
                .apellidos(usuarioRequest.getApellidos())
                .nombres(usuarioRequest.getNombres())
                .rol(rol)
                .createdAt(LocalDateTime.now())
                .lastLogin(null)
                .email(usuarioRequest.getEmail())
                .carnetIdentidad(usuarioRequest.getCarnetIdentidad())
                .createdBy(usuarioRequest.getCreadorID())
                .celular(usuarioRequest.getCelular())
                .genero(usuarioRequest.getGenero())
                .fechaNacimiento(usuarioRequest.getFechaNacimiento())
                .password(passwordEncoder.encode(password))
                .build();

        try {
            usuarioRepository.save(usuario);
            log.info("Usuario Creado");
        } catch (Exception e){
            UsuarioServiceException usuarioServiceCustomException = new UsuarioServiceException("Ya existe una cuenta con ese email", "400");
            throw usuarioServiceCustomException;
        }

        return usuario.getUsuarioId();
    }

    @Override
    public UsuarioCompleteResponse getUsuarioById(long usuarioId) {
        log.info("Usuario Service: Sacando Usuario con id:" + usuarioId);
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(
                        () -> userDoesntExist(usuarioId)
                );
        UsuarioCompleteResponse usuarioResponse = new UsuarioCompleteResponse();
        BeanUtils.copyProperties(usuario, usuarioResponse);
        usuarioResponse.setRol(usuario.getRol().getNombre());
        log.debug("CREATED AT: "+usuario.getCreatedAt());
        usuarioResponse.setCreatedAt(usuario.getCreatedAt());

        if(usuario.getCreatedBy() != 0) {
            Usuario creador = usuarioRepository.findById(usuario.getCreatedBy())
                    .orElseThrow(
                            () -> userDoesntExist(usuario.getCreatedBy())
                    );
            usuarioResponse.setCreador(creador.getNombres().split(" ")[0]+" "+creador.getApellidos().split(" ")[0]);
        } else {
            usuarioResponse.setCreador("Sistema");
        }

        return usuarioResponse;
    }

    @Override
    public List<UsuarioResponse> getAllUsers() {
        log.info("Sacando todos los usuarios");
        List<Usuario> usuarios = usuarioRepository.findAll();
        List<UsuarioResponse> usuariosResponse = usuarios
                .stream()
                .map(usuario ->{
                    UsuarioResponse usuarioResponse = new UsuarioResponse();
                    BeanUtils.copyProperties(usuario, usuarioResponse);
                    usuarioResponse.setRol(usuario.getRol().getNombre());
                    return usuarioResponse;
                }).collect(Collectors.toList());
        return usuariosResponse;
    }

    @Override
    public void updatePasswordUsuario(long usuarioId, UpdatePasswordRequest updatePasswordRequest) {
        log.info("Actualizando password de usuario con id: "+usuarioId);
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(
                        () -> userDoesntExist(usuarioId)
                );
        usuario.setPassword(passwordEncoder.encode(updatePasswordRequest.getPassword()));
        usuarioRepository.save(usuario);
    }

    @Override
    public void deleteUsuarioById(long usuarioId) {
        log.info("Borrando Usuario");
        usuarioRepository.deleteById(usuarioId);
    }
}
