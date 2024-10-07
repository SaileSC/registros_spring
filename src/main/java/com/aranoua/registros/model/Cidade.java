package com.aranoua.registros.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

// A anotação @Entity indica que essa classe é uma entidade JPA, mapeada para uma tabela no banco de dados.
@Entity
// Define explicitamente o nome da tabela no banco de dados como "cidade".
@Table(name = "cidade")
// As anotações @Getter e @Setter são do Lombok, e geram automaticamente os métodos getters e setters para todos os atributos.
@Getter
@Setter
// Gera um construtor com todos os parâmetros.
@AllArgsConstructor
// Gera um construtor sem parâmetros.
@NoArgsConstructor
public class Cidade {

    // A anotação @Id indica que este campo é a chave primária da tabela.
    // @GeneratedValue define que o valor do campo será gerado automaticamente pelo banco de dados,
    // usando a estratégia de auto incremento (IDENTITY).
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    // Campo que armazena o nome da cidade.
    private String nome;

    // Relacionamento @ManyToOne indica que muitas cidades podem estar associadas a um único estado.
    // Estado será mapeado automaticamente como uma chave estrangeira.
    @ManyToOne
    private Estado estado;

    // Relacionamento @OneToMany indica que uma cidade pode estar associada a muitas pessoas.
    // O atributo "mappedBy" indica que a relação é bidirecional, e será mapeada pelo campo "cidade" na classe Pessoa.
    @OneToMany(mappedBy = "cidade")
    private List<Pessoa> pessoas;
}

