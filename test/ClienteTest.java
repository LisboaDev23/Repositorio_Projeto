import dao.ClienteDAO;
import dao.IClienteDAO;
import dao.IProdutoDAO;
import dao.ProdutoDAO;
import domain.Cliente;
import domain.Produto;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class ClienteTest {

    @Test
    public void cadastrarTest() throws Exception {
        IClienteDAO dao = new ClienteDAO();

        Cliente cliente = new Cliente();
        cliente.setCpf(19134960309l);
        cliente.setNome("Gabriel Pereira Lisboa");
        cliente.setIdade(21);
        cliente.setIdProduto(35l);


        Integer qtd = dao.cadastrar(cliente);
        assertTrue(qtd == 1);

        Cliente clienteBD = dao.consultar(cliente.getCpf());
        assertNotNull(clienteBD);
        assertNotNull(clienteBD.getId());
        assertEquals(cliente.getCpf(), clienteBD.getCpf());
        assertEquals(cliente.getNome(), clienteBD.getNome());

        Integer qtdDel = dao.excluir(clienteBD);
        assertNotNull(qtdDel);
    }
    @Test
    public void consultarTest() throws Exception{
        IClienteDAO dao = new ClienteDAO();
        Cliente cliente = new Cliente();
        cliente.setCpf(19134960309l);
        cliente.setNome("Gabriel Pereira Lisboa");
        cliente.setIdade(21);
        cliente.setIdProduto(35l);
        Integer quantidadeProdutosCadastrados = dao.cadastrar(cliente);
        Cliente clienteBD = dao.consultar(cliente.getCpf());
        assertEquals(clienteBD.getCpf(),cliente.getCpf());
        assertEquals(clienteBD.getNome(),cliente.getNome());
        assertEquals(clienteBD.getIdade(),cliente.getIdade());
        assertEquals(clienteBD.getIdProduto(),cliente.getIdProduto());
        Integer qtdDeletados = dao.excluir(clienteBD);
    }
    @Test
    public void buscarTodosTest()throws Exception {
        IProdutoDAO dao = new ProdutoDAO();
        dao.buscarTodos();
    }
    @Test
    public void alterarTest()throws Exception{
        IClienteDAO dao = new ClienteDAO();
        Cliente cliente = new Cliente();
        cliente.setCpf(19134960309l);
        cliente.setNome("Gabriel Pereira Lisboa");
        cliente.setIdade(21);
        cliente.setIdProduto(35l);
        Integer quantidadeProdutosCadastrados = dao.cadastrar(cliente);
        dao.alterar(cliente);
    }


}