package com.aranoua.registros.dto.estado;

import com.aranoua.registros.model.Estado;
import com.aranoua.registros.model.Pais;
import com.aranoua.registros.repository.PaisRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Optional;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EstadoInputDTO {
    private String nome;
    private String sigla;
    private String pais;

    public Estado build(PaisRepository repository){
            Estado estado = new Estado();
            estado.setNome(this.nome);
            estado.setSigla(this.sigla);
            estado.setPais(repository.findByNome(this.pais));
            return estado;
    }
}
