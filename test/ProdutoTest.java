import dao.IProdutoDAO;
import dao.ProdutoDAO;
import domain.Produto;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
public class ProdutoTest {
    @Test
    public void cadastrarTest() throws Exception {
        IProdutoDAO dao = new ProdutoDAO();
        Produto produto = new Produto();
        produto.setCodigo(1l);
        produto.setNome("Iphone");
        produto.setPreco(5800l);
        produto.setQtd_estoque(100);
        Integer quantidadeProdutosCadastrados = dao.cadastrar(produto);
        assertTrue(quantidadeProdutosCadastrados==1);

        Produto produtoBD = dao.consultar(produto.getCodigo());
        assertNotNull(produtoBD);
        assertNotNull(produtoBD.getId());
        assertEquals(produto.getNome(),produtoBD.getNome());

        Integer qtdDeletados = dao.excluir(produtoBD);
        assertNotNull(qtdDeletados);
    }

    @Test
    public void consultarTest() throws Exception{
        IProdutoDAO dao = new ProdutoDAO();
        Produto produto = new Produto();
        produto.setCodigo(1l);
        produto.setNome("Iphone");
        produto.setPreco(5800l);
        produto.setQtd_estoque(100);
        Integer quantidadeProdutosCadastrados = dao.cadastrar(produto);
        Produto produtoBD = dao.consultar(produto.getCodigo());
        assertEquals(produtoBD.getNome(),produto.getNome());
        assertEquals(produtoBD.getCodigo(),produto.getCodigo());
        assertEquals(produtoBD.getPreco(),produto.getPreco());
        assertEquals(produtoBD.getQtd_estoque(),produto.getQtd_estoque());
        Integer qtdDeletados = dao.excluir(produtoBD);
    }
    @Test
    public void buscarTodosTest()throws Exception {
        IProdutoDAO dao = new ProdutoDAO();
        dao.buscarTodos();
    }
    @Test
    public void alterarTest()throws Exception{
        IProdutoDAO dao = new ProdutoDAO();
        Produto produto = new Produto();
        produto.setCodigo(39l);
        produto.setNome("Poco X5 pro");
        produto.setPreco(1950l);
        produto.setQtd_estoque(150);
        Integer quantidadeProdutosCadastrados = dao.cadastrar(produto);
        dao.alterar(produto);
    }


}
