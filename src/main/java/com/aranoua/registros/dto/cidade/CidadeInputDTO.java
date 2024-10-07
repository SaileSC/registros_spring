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
// Classe DTO (Data Transfer Object) para representar os dados de entrada ao criar uma nova cidade.
// Esta classe é usada para transferir dados do cliente para o servidor sem expor a entidade completa.
public class CidadeInputDTO {

    // Nome da cidade que será inserido.
    private String nome;

    // Nome do estado associado à cidade que será inserido.
    private String estado;

    // Método que converte o DTO para a entidade Cidade.
    // Ele busca o estado correspondente no repositório e o associa à cidade.
    public Cidade build(EstadoRepository repository) {
        // Cria uma nova instância da entidade Cidade.
        Cidade cidade = new Cidade();

        // Define o nome da cidade com o valor passado no DTO.
        cidade.setNome(this.nome);

        // Usa o repositório para buscar a entidade Estado pelo nome fornecido no DTO e
        // associa o Estado encontrado à nova Cidade.
        cidade.setEstado(repository.findByNome(this.estado));

        // Retorna a nova instância da entidade Cidade.
        return cidade;
    }
}

