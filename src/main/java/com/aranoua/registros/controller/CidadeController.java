package com.aranoua.registros.controller;

import com.aranoua.registros.dto.cidade.CidadeInputDTO;
import com.aranoua.registros.dto.cidade.CidadeOutputDTO;
import com.aranoua.registros.model.Cidade;
import com.aranoua.registros.repository.CidadeRepository;
import com.aranoua.registros.repository.EstadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/cidade")
public class CidadeController {
    @Autowired
    CidadeRepository cidadeRepository;
    @Autowired
    EstadoRepository estadoRepository;

    @GetMapping
    public ResponseEntity<List<CidadeOutputDTO>> list(){
        List<Cidade> cidades = cidadeRepository.findAll();
        if(cidades.isEmpty()) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        List<CidadeOutputDTO> cidadeDTOs = cidades.stream()
                .map(CidadeOutputDTO::new)
                .toList();
        return new ResponseEntity<>(cidadeDTOs, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CidadeOutputDTO> read(@PathVariable long id){
        Optional<Cidade> cidade = cidadeRepository.findById(id);
        return cidade.map(value ->
                new ResponseEntity<>(new CidadeOutputDTO(value), HttpStatus.OK)).orElseGet(
                () -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody CidadeInputDTO body){
        try {
            CidadeOutputDTO novaCidadeDTO = new CidadeOutputDTO(cidadeRepository.save(body.build(estadoRepository)));
            return new ResponseEntity<>(novaCidadeDTO, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getCause(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable long id, @RequestBody CidadeInputDTO body){
        try {
            Optional<Cidade> cidade = cidadeRepository.findById(id);
            if(cidade.isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            Cidade cidadeAntiga = cidade.get();
            cidadeAntiga.setNome(body.getNome());
            cidadeAntiga.setEstado(estadoRepository.findByNome(body.getEstado()));
            return new ResponseEntity<>(new CidadeOutputDTO(cidadeRepository.save(cidadeAntiga)), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getCause(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable long id){
        try {
            Optional<Cidade> cidade = cidadeRepository.findById(id);
            if(cidade.isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            cidadeRepository.delete(cidade.get());
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getCause(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
