package com.aranoua.registros.repository;

import com.aranoua.registros.model.Pais;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaisRepository extends JpaRepository<Pais, Long> {
    Pais findByNome(String nome);
}
