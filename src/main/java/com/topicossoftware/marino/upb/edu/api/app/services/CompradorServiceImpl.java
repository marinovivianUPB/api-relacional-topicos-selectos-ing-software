package com.topicossoftware.marino.upb.edu.api.app.services;

import com.topicossoftware.marino.upb.edu.api.app.exceptions.CompradorServiceException;
import com.topicossoftware.marino.upb.edu.api.app.exceptions.UsuarioServiceException;
import com.topicossoftware.marino.upb.edu.api.domain.entity.Comprador;
import com.topicossoftware.marino.upb.edu.api.domain.entity.Rol;
import com.topicossoftware.marino.upb.edu.api.domain.entity.Usuario;
import com.topicossoftware.marino.upb.edu.api.domain.interfaces.app.CompradorService;
import com.topicossoftware.marino.upb.edu.api.domain.interfaces.infrastructure.CompradorRepository;
import com.topicossoftware.marino.upb.edu.api.domain.interfaces.infrastructure.UsuarioRepository;
import com.topicossoftware.marino.upb.edu.api.domain.model.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
public class CompradorServiceImpl implements CompradorService {

    @Autowired
    private CompradorRepository compradorRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;

    private CompradorServiceException compradorDoesntExist(long usuarioId){
        CompradorServiceException compradorServiceCustomException = new CompradorServiceException("No existe el usuario con id "+usuarioId, "400");

        return compradorServiceCustomException;
    }

    private UsuarioServiceException userDoesntExist(long usuarioId){
        UsuarioServiceException usuarioServiceCustomException = new UsuarioServiceException("No existe el usuario con id "+usuarioId, "400");

        return usuarioServiceCustomException;
    }
    @Override
    public long addComprador(CompradorRequest compradorRequest) {
        log.info("Comprador Service: Creando Comprador:" + compradorRequest.getEmail());

        Usuario usuario = usuarioRepository.findById(compradorRequest.getUsuarioId())
                .orElseThrow(
                        () -> userDoesntExist(compradorRequest.getUsuarioId())
                );

        Comprador comprador = Comprador.builder()
                .apellidos(compradorRequest.getApellidos())
                .nombres(compradorRequest.getNombres())
                .createdAt(LocalDateTime.now())
                .email(compradorRequest.getEmail())
                .carnetIdentidad(compradorRequest.getCarnetIdentidad())
                .usuario(usuario)
                .celular(compradorRequest.getCelular())
                .build();

        try {
            compradorRepository.save(comprador);
            log.info("Comprador Creado");
        } catch (Exception e){
            CompradorServiceException compradorServiceException = new CompradorServiceException("Ya existe una cuenta con ese email", "400");
            throw compradorServiceException;
        }

        return comprador.getCompradorId();
    }

    @Override
    public CompradorCompleteResponse getCompradorById(long compradorId) {
        log.info("Comprador Service: Sacando Comprador con id:" + compradorId);
        Comprador comprador = compradorRepository.findById(compradorId)
                .orElseThrow(
                        () -> compradorDoesntExist(compradorId)
                );
        CompradorCompleteResponse compradorResponse = new CompradorCompleteResponse();
        BeanUtils.copyProperties(comprador, compradorResponse);
        return compradorResponse;
    }

    @Override
    public List<CompradorResponse> getCompradores() {
        log.info("Sacando todos los compradores");
        List<Comprador> compradores = compradorRepository.findAll();
        List<CompradorResponse> compradoresResponse = compradores
                .stream()
                .map(comprador ->{
                    CompradorResponse compradorResponse = new CompradorResponse();
                    BeanUtils.copyProperties(comprador, compradorResponse);
                    return compradorResponse;
                }).collect(Collectors.toList());
        return compradoresResponse;
    }

    @Override
    public void deleteCompradorById(long compradorId) {
        log.info("Borrando Comprador");
        compradorRepository.deleteById(compradorId);
    }
}
