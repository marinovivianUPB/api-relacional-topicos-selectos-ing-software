package com.topicossoftware.marino.upb.edu.api.domain.interfaces.app;

import com.topicossoftware.marino.upb.edu.api.domain.model.UpdatePasswordRequest;
import com.topicossoftware.marino.upb.edu.api.domain.model.UsuarioCompleteResponse;
import com.topicossoftware.marino.upb.edu.api.domain.model.UsuarioRequest;
import com.topicossoftware.marino.upb.edu.api.domain.model.UsuarioResponse;

import java.util.List;

public interface UsuarioService {

    long addUsuario(UsuarioRequest usuarioRequest);

    UsuarioCompleteResponse getUsuarioById(long usuarioId);

    List<UsuarioResponse> getAllUsers();

    void updatePasswordUsuario(long usuarioId, UpdatePasswordRequest updatePasswordRequest);

    /*PARA PRUEBAS*/
    void deleteUsuarioById(long usuarioId);
}