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
// Classe DTO (Data Transfer Object) para representar os dados de saída ao enviar uma resposta
// com informações de uma cidade. Essa classe evita expor a entidade Cidade diretamente.
public class CidadeOutputDTO {

    // ID da cidade.
    private long id;

    // Nome da cidade.
    private String nome;

    // Nome do estado ao qual a cidade pertence.
    private String estado;

    // Construtor que recebe uma instância de Cidade e transforma seus dados no formato do DTO.
    // Extrai o nome do estado associado à cidade.
    public CidadeOutputDTO(Cidade cidade) {
        // Atribui o ID da cidade ao campo id do DTO.
        this.id = cidade.getId();

        // Atribui o nome da cidade ao campo nome do DTO.
        this.nome = cidade.getNome();

        // Atribui o nome do estado ao campo estado do DTO,
        // obtendo o estado associado e extraindo seu nome.
        this.estado = cidade.getEstado().getNome();
    }
}

