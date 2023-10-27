package main.java.dao;

import main.java.domain.Curso;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class CursoDAO implements ICursoDAO {
    @Override
    public Curso cadastrar(Curso curso) {
        EntityManagerFactory entityManagerFactory = //necessario criar uma variavel de Fabrica de gerenciadores de entidades
                Persistence.createEntityManagerFactory("ExemploJPA");//essa variavel recebe um metodo de Persistent que cria essa fábrica e digo o nome dela
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        //crio a variável gerenciadora de entidades, onde nela vou chamar um metodo de criar um gerenciador de entidades.
        entityManager.getTransaction().begin();//chamando um metodo do gerenciador que pega a transação que irá ocorrer no framework e inicia ela.
        entityManager.persist(curso);//salvar o curso no banco de dados.
        entityManager.getTransaction().commit();//é necessário fazer o commit da transação que ocorreu.

        entityManager.close();//fechar a conexão com o banco de dados que a variável gerenciadora de entidades abriu;
        entityManagerFactory.close();//fechar a conexão com o banco de dados que a variável de fabrica de gerenciadora de entidades abriu;
        return curso;
    }
}
