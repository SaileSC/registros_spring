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

// Controlador REST responsável por gerenciar as operações CRUD da entidade Pessoa.
// Ele define as rotas da API relacionadas a "Pessoa" e interage com os repositórios.
@RestController
// Define o caminho base para acessar os recursos de Pessoa na API.
@RequestMapping("/api/pessoa")
public class PessoaController {

    // Injeção de dependência automática do repositório de Pessoa.
    @Autowired
    PessoaRepository pessoaRepository;

    // Injeção de dependência automática do repositório de Cidade,
    // para interagir com a entidade Cidade quando necessário.
    @Autowired
    CidadeRepository cidadeRepository;

    // Método GET para listar todas as pessoas cadastradas.
    // Retorna uma lista de objetos PessoaOutputDTO como resposta.
    @GetMapping
    public ResponseEntity<List<PessoaOutputDTO>> list() {
        // Busca todas as pessoas no banco de dados.
        List<Pessoa> pessoas = pessoaRepository.findAll();

        // Se a lista estiver vazia, retorna um status HTTP 204 (No Content).
        if (pessoas.isEmpty()) return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        // Mapeia cada entidade Pessoa para um DTO e retorna com status HTTP 200 (OK).
        List<PessoaOutputDTO> pessoaDTOs = pessoas.stream()
                .map(PessoaOutputDTO::new)
                .toList();
        return new ResponseEntity<>(pessoaDTOs, HttpStatus.OK);
    }

    // Método GET para buscar uma pessoa específica pelo ID.
    // Retorna o DTO da pessoa ou 404 (Not Found) se não existir.
    @GetMapping("/{id}")
    public ResponseEntity<PessoaOutputDTO> read(@PathVariable long id) {
        // Busca a pessoa pelo ID.
        Optional<Pessoa> pessoa = pessoaRepository.findById(id);

        // Se a pessoa for encontrada, retorna seu DTO com status HTTP 200 (OK),
        // caso contrário, retorna status 404 (Not Found).
        return pessoa.map(value ->
                new ResponseEntity<>(new PessoaOutputDTO(value), HttpStatus.OK)).orElseGet(
                () -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Método POST para criar uma nova pessoa.
    // Recebe um DTO de entrada (PessoaInputDTO) e retorna o DTO da nova pessoa criada com status 201 (Created).
    @PostMapping
    public ResponseEntity<?> create(@RequestBody PessoaInputDTO body) {
        try {
            // Converte o DTO de entrada para a entidade Pessoa e salva no banco de dados.
            PessoaOutputDTO novaPessoaDTO = new PessoaOutputDTO(pessoaRepository.save(body.build(cidadeRepository)));

            // Retorna o DTO da nova pessoa criada com status 201 (Created).
            return new ResponseEntity<>(novaPessoaDTO, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            // Em caso de erro, retorna o erro com status 500 (Internal Server Error).
            return new ResponseEntity<>(e.getCause(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Método PUT para atualizar uma pessoa existente pelo ID.
    // Recebe um DTO de entrada (PessoaInputDTO) e atualiza a pessoa correspondente.
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable long id, @RequestBody PessoaInputDTO body) {
        try {
            // Busca a pessoa pelo ID.
            Optional<Pessoa> pessoa = pessoaRepository.findById(id);

            // Se a pessoa não for encontrada, retorna status 404 (Not Found).
            if (pessoa.isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

            // Atualiza os dados da pessoa existente com os novos valores.
            Pessoa pessoaAntiga = pessoa.get();
            pessoaAntiga.setNome(body.getNome());
            pessoaAntiga.setEmail(body.getEmail());
            pessoaAntiga.setTelefone(body.getTelefone());
            pessoaAntiga.setCidade(cidadeRepository.findByNome(body.getCidade()));

            // Salva as alterações no banco de dados e retorna o DTO atualizado com status 200 (OK).
            return new ResponseEntity<>(new PessoaOutputDTO(pessoaRepository.save(pessoaAntiga)), HttpStatus.OK);
        } catch (RuntimeException e) {
            // Em caso de erro, retorna o erro com status 500 (Internal Server Error).
            return new ResponseEntity<>(e.getCause(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Método DELETE para excluir uma pessoa pelo ID.
    // Retorna 200 (OK) se a pessoa foi excluída, ou 404 (Not Found) se a pessoa não existir.
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable long id) {
        try {
            // Busca a pessoa pelo ID.
            Optional<Pessoa> pessoa = pessoaRepository.findById(id);

            // Se a pessoa não for encontrada, retorna status 404 (Not Found).
            if (pessoa.isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

            // Exclui a pessoa do banco de dados.
            pessoaRepository.delete(pessoa.get());

            // Retorna status 200 (OK) para indicar que a exclusão foi bem-sucedida.
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (RuntimeException e) {
            // Em caso de erro, retorna o erro com status 500 (Internal Server Error).
            return new ResponseEntity<>(e.getCause(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

