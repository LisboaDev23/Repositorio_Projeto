package main.java.domain;

import javax.persistence.*;
import java.util.List;

@Entity //annotation que diz que essa classe é uma entidade a ser utilizada no banco de dados
@Table(name = "TB_CURSO") //anottation da persistence que vai dizer qual o nome da tabela
public class Curso {

    @Id //annotation que defini que essa propriedade será o id na tabela, onde será a chave primária
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "curso_seq")
//annotation responsável por gerar na tabela, os valores da sequencia
    @SequenceGenerator(name = "curso_seq", sequenceName = "sq_curso", initialValue = 1, allocationSize = 1)
    /*
    Em relação a essa anotação de geração de sequência, no name eu coloco o nome do gerador que deve ser passado na
    anotação de geração de valor, onde nela especifica o tipo de valor que será gerado(nesse caso, sequência), e logo
    deve ser dado uma String pra dizer o nome do gerador nesse caso o curso_seq. Depois de dizer o nome do gerador,
    passar o nome que será dado a sequência (sq_curso), depois o valor inicial, e depois a sua incrementação que
    nesse caso é o allocationSize que representa o increment no SQL.
     */
    private Long id;
    @Column(name = "CODIGO_CURSO", length = 10, nullable = false, unique = true)
    /*nessa anotação estou fazendo o mapeamento da minha propriedade dentro da tabela SQL, nesse caso
    estou definindo que terá uma coluna na minha tabela com o nome=?, tamanho, pode ser nula ou não,
    única ou não, então aqui vou determinar as características da minha coluna.
     */
    private Long codigoCurso;
    @Column(name = "NOME", length = 50, nullable = false, unique = true)
    private String nome;
    @Column(name = "DESCRICAO", length = 250)
    private String descricao;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCodigoCurso() {
        return codigoCurso;
    }

    public void setCodigoCurso(Long codigoCurso) {
        this.codigoCurso = codigoCurso;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
