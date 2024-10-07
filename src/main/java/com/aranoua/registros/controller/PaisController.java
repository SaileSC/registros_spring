package com.aranoua.registros.controller;


import com.aranoua.registros.dto.pais.PaisInputDTO;
import com.aranoua.registros.dto.pais.PaisOutputDTO;
import com.aranoua.registros.model.Pais;
import com.aranoua.registros.repository.PaisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/api/pais")
public class PaisController {
    @Autowired
    PaisRepository paisRepository;

    @GetMapping
    public ResponseEntity<List<PaisOutputDTO>> list(){
        List<Pais> paises = paisRepository.findAll();
        if(paises.isEmpty()) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        List<PaisOutputDTO> paisesDTO = paises.stream()
                .map(PaisOutputDTO::new)
                .toList();
        return new ResponseEntity<>(paisesDTO, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaisOutputDTO> read(@PathVariable long id){
        Optional<Pais> pais = paisRepository.findById(id);
        return pais.map(value ->
                new ResponseEntity<>(new PaisOutputDTO(value), HttpStatus.OK)).orElseGet(
                        () -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody PaisInputDTO body){
        try {
            PaisOutputDTO novoPaisDTO = new PaisOutputDTO(paisRepository.save(body.build()));
            return new ResponseEntity<>(novoPaisDTO, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getCause(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable long id, @RequestBody PaisInputDTO body){
        try {
            Optional<Pais> pais = paisRepository.findById(id);
            if(pais.isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            Pais antigoPais = pais.get();
            antigoPais.setNome(body.getNome());
            antigoPais.setSigla(body.getSigla());
            return new ResponseEntity<>(new PaisOutputDTO(paisRepository.save(antigoPais)), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getCause(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable long id){
        try {
            Optional<Pais> pais = paisRepository.findById(id);
            if(pais.isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            paisRepository.delete(pais.get());
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getCause(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
