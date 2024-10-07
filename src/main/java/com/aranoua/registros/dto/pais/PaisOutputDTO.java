package com.aranoua.registros.dto.pais;

import com.aranoua.registros.model.Pais;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaisOutputDTO {
    private long id;
    private String nome;
    private String sigla;

    public PaisOutputDTO(Pais pais){
        this.id = pais.getId();
        this.nome = pais.getNome();
        this.sigla = pais.getSigla();
    }
}
