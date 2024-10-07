package com.aranoua.registros.dto.pessoa;

import com.aranoua.registros.model.Pessoa;
import com.aranoua.registros.repository.CidadeRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PessoaInputDTO {
    private String nome;
    private String email;
    private String telefone;
    private String cidade;

    public Pessoa build(CidadeRepository repository){
        Pessoa pessoa = new Pessoa();
        pessoa.setNome(this.nome);
        pessoa.setEmail(this.email);
        pessoa.setTelefone(this.telefone);
        pessoa.setCidade(repository.findByNome(this.cidade));
        return pessoa;
    }
}
