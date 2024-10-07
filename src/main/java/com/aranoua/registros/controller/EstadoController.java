package com.aranoua.registros.controller;

import com.aranoua.registros.dto.estado.EstadoInputDTO;
import com.aranoua.registros.dto.estado.EstadoOutputDTO;
import com.aranoua.registros.model.Estado;
import com.aranoua.registros.repository.EstadoRepository;
import com.aranoua.registros.repository.PaisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/estado")
public class EstadoController {
    @Autowired
    EstadoRepository estadoRepository;
    @Autowired
    PaisRepository paisRepository;


    @GetMapping
    public ResponseEntity<List<EstadoOutputDTO>> list(){
        List<Estado> estado = estadoRepository.findAll();
        if(estado.isEmpty()) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        List<EstadoOutputDTO> estadoDTO = estado.stream()
                .map(EstadoOutputDTO::new)
                .toList();
        return new ResponseEntity<>(estadoDTO, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EstadoOutputDTO> read(@PathVariable long id){
        Optional<Estado> pais = estadoRepository.findById(id);
        return pais.map(value ->
                new ResponseEntity<>(new EstadoOutputDTO(value), HttpStatus.OK)).orElseGet(
                () -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody EstadoInputDTO body){
        try {
            EstadoOutputDTO novoPaisDTO = new EstadoOutputDTO(estadoRepository.save(body.build(paisRepository)));
            return new ResponseEntity<>(novoPaisDTO, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getCause(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable long id, @RequestBody EstadoInputDTO body){
        try {
            Optional<Estado> pais = estadoRepository.findById(id);
            if(pais.isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            Estado estadoAntigo = pais.get();
            estadoAntigo.setNome(body.getNome());
            estadoAntigo.setSigla(body.getSigla());
            estadoAntigo.setPais(paisRepository.findByNome(body.getPais()));
            return new ResponseEntity<>(new EstadoOutputDTO(estadoRepository.save(estadoAntigo)), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getCause(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable long id){
        try {
            Optional<Estado> estado = estadoRepository.findById(id);
            if(estado.isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            estadoRepository.delete(estado.get());
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getCause(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
