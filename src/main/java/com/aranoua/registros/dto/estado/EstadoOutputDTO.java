package com.aranoua.registros.dto.estado;

import com.aranoua.registros.model.Estado;
import com.aranoua.registros.model.Pais;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EstadoOutputDTO {
    private long id;
    private String nome;
    private String sigla;
    private String pais;

    public EstadoOutputDTO(Estado estado){
        this.id = estado.getId();
        this.nome = estado.getNome();
        this.sigla = estado.getSigla();
        this.pais = estado.getPais().getNome();
    }
}
