package com.aranoua.registros.repository;

import com.aranoua.registros.model.Pais;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// Anotação @Repository para indicar que esta interface faz parte da camada de persistência de dados
// e gerencia a comunicação com o banco de dados.
@Repository
public interface PaisRepository extends JpaRepository<Pais, Long> {

    // Método personalizado para buscar um país no banco de dados pelo nome.
    // O Spring Data JPA vai gerar automaticamente a implementação deste método com base na convenção de nome.
    Pais findByNome(String nome);
}

