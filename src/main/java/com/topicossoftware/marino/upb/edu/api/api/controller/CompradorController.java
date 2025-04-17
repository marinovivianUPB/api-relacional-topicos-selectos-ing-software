package com.topicossoftware.marino.upb.edu.api.api.controller;

import com.topicossoftware.marino.upb.edu.api.domain.interfaces.app.CompradorService;
import com.topicossoftware.marino.upb.edu.api.domain.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/compradores")
public class CompradorController {
    @Autowired
    private CompradorService compradorService;

    @PostMapping("/")
    public ResponseEntity<Long> addComprador(@RequestBody CompradorRequest compradorRequest, @RequestHeader("token") String token){
        long compradorId = compradorService.addComprador(compradorRequest);
        return new ResponseEntity<>(compradorId, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompradorCompleteResponse> getCompradorById(@PathVariable("id") long compradorId, @RequestHeader("token") String token){
        CompradorCompleteResponse compradorResponse = compradorService.getCompradorById(compradorId);
        return new ResponseEntity<>(compradorResponse, HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<List<CompradorResponse>> getAllCompradores(@RequestHeader("token") String token){
        List<CompradorResponse> compradorResponse;
        compradorResponse = compradorService.getCompradores();

        return new ResponseEntity<>(compradorResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteCompradorById(@PathVariable("id") long compradorId,@RequestHeader("token") String token){
        compradorService.deleteCompradorById(compradorId);
    }
}