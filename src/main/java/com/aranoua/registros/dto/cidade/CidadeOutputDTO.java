package com.aranoua.registros.dto.cidade;

import com.aranoua.registros.model.Cidade;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CidadeOutputDTO {
    private long id;
    private String nome;
    private String estado;

    public CidadeOutputDTO(Cidade cidade){
        this.id = cidade.getId();
        this.nome = cidade.getNome();
        this.estado = cidade.getEstado().getNome();
    }
}
