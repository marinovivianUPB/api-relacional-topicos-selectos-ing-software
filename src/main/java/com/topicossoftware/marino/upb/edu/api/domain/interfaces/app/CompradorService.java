package com.topicossoftware.marino.upb.edu.api.domain.interfaces.app;

import com.topicossoftware.marino.upb.edu.api.domain.model.*;

import java.util.List;

public interface CompradorService {

    long addComprador(CompradorRequest compradorRequest);

    CompradorCompleteResponse getCompradorById(long compradorId);

    List<CompradorResponse> getCompradores();

    /*PARA PRUEBAS*/
    void deleteCompradorById(long compradorId);
}