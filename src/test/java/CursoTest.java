package test.java;

import main.java.dao.CursoDAO;
import main.java.dao.ICursoDAO;
import main.java.domain.Curso;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.After;
import org.junit.Test;


public class CursoTest {
    private ICursoDAO cursoDAO;

    public CursoTest(){
        cursoDAO = new CursoDAO();
    }

    @Test
    public void cadastrar(){
        //Para cadastrar precisamos criar um curso (Instanciar)
        Curso curso = new Curso();
        curso.setCodigoCurso(5559851225L);
        curso.setNome("Engenharia de Software");
        curso.setDescricao("Um dos melhores cursos da área de tecnologia");
        //depois de setarmos as informações desse curso, vamos definir o dao desse curso para testarmos a sua conexão com o banco de dados
        //ICursoDAO cursoDAO = new CursoDAO();isso aqui deve ser uma propriedade de classe para podermos chamar nos metodos, e somente no construtor que ela deve ser instanciada
        curso = cursoDAO.cadastrar(curso);

        assertNotNull(curso);
        assertNotNull(curso.getId());
    }
}
