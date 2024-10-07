package com.aranoua.registros.controller;

import com.aranoua.registros.dto.pessoa.PessoaInputDTO;
import com.aranoua.registros.dto.pessoa.PessoaOutputDTO;
import com.aranoua.registros.model.Pessoa;
import com.aranoua.registros.repository.CidadeRepository;
import com.aranoua.registros.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/pessoa")
public class PessoaController {
    @Autowired
    PessoaRepository pessoaRepository;
    @Autowired
    CidadeRepository cidadeRepository;

    @GetMapping
    public ResponseEntity<List<PessoaOutputDTO>> list(){
        List<Pessoa> pessoas = pessoaRepository.findAll();
        if(pessoas.isEmpty()) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        List<PessoaOutputDTO> pessoaDTOs = pessoas.stream()
                .map(PessoaOutputDTO::new)
                .toList();
        return new ResponseEntity<>(pessoaDTOs, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PessoaOutputDTO> read(@PathVariable long id){
        Optional<Pessoa> pessoa = pessoaRepository.findById(id);
        return pessoa.map(value ->
                new ResponseEntity<>(new PessoaOutputDTO(value), HttpStatus.OK)).orElseGet(
                () -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody PessoaInputDTO body){
        try {
            PessoaOutputDTO novaPessoaDTO = new PessoaOutputDTO(pessoaRepository.save(body.build(cidadeRepository)));
            return new ResponseEntity<>(novaPessoaDTO, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getCause(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable long id, @RequestBody PessoaInputDTO body){
        try {
            Optional<Pessoa> pessoa = pessoaRepository.findById(id);
            if(pessoa.isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            Pessoa pessoaAntiga = pessoa.get();
            pessoaAntiga.setNome(body.getNome());
            pessoaAntiga.setEmail(body.getEmail());
            pessoaAntiga.setTelefone(body.getTelefone());
            pessoaAntiga.setCidade(cidadeRepository.findByNome(body.getCidade()));
            return new ResponseEntity<>(new PessoaOutputDTO(pessoaRepository.save(pessoaAntiga)), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getCause(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable long id){
        try {
            Optional<Pessoa> pessoa = pessoaRepository.findById(id);
            if(pessoa.isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            pessoaRepository.delete(pessoa.get());
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getCause(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
