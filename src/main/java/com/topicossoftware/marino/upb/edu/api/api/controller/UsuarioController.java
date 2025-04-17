package com.topicossoftware.marino.upb.edu.api.api.controller;

import com.topicossoftware.marino.upb.edu.api.domain.interfaces.app.UsuarioService;
import com.topicossoftware.marino.upb.edu.api.domain.model.UsuarioCompleteResponse;
import com.topicossoftware.marino.upb.edu.api.domain.model.UsuarioRequest;
import com.topicossoftware.marino.upb.edu.api.domain.model.UsuarioResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/")
    public ResponseEntity<Long> addUsuario(@RequestBody UsuarioRequest usuarioRequest, @RequestHeader("token") String token){
        long usuarioId = usuarioService.addUsuario(usuarioRequest);
        return new ResponseEntity<>(usuarioId, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioCompleteResponse> getUsuarioById(@PathVariable("id") long usuarioId, @RequestHeader("token") String token){
        UsuarioCompleteResponse usuarioResponse = usuarioService.getUsuarioById(usuarioId);
        return new ResponseEntity<>(usuarioResponse, HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<List<UsuarioResponse>> getAllUsuarios(@RequestHeader("token") String token){
        List<UsuarioResponse> usuarioResponse;
        usuarioResponse = usuarioService.getAllUsers();

        return new ResponseEntity<>(usuarioResponse, HttpStatus.OK);
    }

    /*@PutMapping("/{id}")
    public void updateVariable(@PathVariable("id") long variableId, @RequestBody UpdateVariableRequest updateVariableRequest) {
        variableService.updateVariable(variableId, updateVariableRequest);
    }*/

    @DeleteMapping("/{id}")
    public void deleteUsuarioById(@PathVariable("id") long usuarioId,@RequestHeader("token") String token){
        usuarioService.deleteUsuarioById(usuarioId);
    }
}