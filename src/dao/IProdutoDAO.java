package dao;

import domain.Produto;

import java.util.List;

public interface IProdutoDAO  {
    public Integer cadastrar(Produto produto) throws Exception;
    public Produto consultar(Long codigo) throws Exception;
    public Integer alterar(Produto produto) throws Exception;
    public Integer excluir(Produto produto) throws Exception;
    public List<Produto> buscarTodos() throws Exception;
}
