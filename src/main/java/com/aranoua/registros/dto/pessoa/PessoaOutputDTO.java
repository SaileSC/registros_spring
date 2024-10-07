package com.aranoua.registros.dto.pessoa;

import com.aranoua.registros.model.Pessoa;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PessoaOutputDTO {
    private long id;
    private String nome;
    private String email;
    private String telefone;
    private String cidade;

    public PessoaOutputDTO(Pessoa pessoa){
        this.id = pessoa.getId();
        this.nome = pessoa.getNome();
        this.email = pessoa.getEmail();
        this.telefone = pessoa.getTelefone();
        this.cidade = pessoa.getCidade().getNome();
    }
}
