package com.aranoua.registros.dto.cidade;

import com.aranoua.registros.model.Cidade;
import com.aranoua.registros.repository.EstadoRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CidadeInputDTO {
    private String nome;
    private String estado;

    public Cidade build(EstadoRepository repository){
        Cidade cidade = new Cidade();
        cidade.setNome(this.nome);
        cidade.setEstado(repository.findByNome(this.estado));
        return cidade;
    }
}
