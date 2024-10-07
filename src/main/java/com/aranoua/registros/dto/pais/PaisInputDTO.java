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
public class PaisInputDTO {
    private String nome;
    private String sigla;

    public Pais build(){
        Pais pais = new Pais();
        pais.setNome(this.nome);
        pais.setSigla(this.sigla);
        return pais;
    }
}
