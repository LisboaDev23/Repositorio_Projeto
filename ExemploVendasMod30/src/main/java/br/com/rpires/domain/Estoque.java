package br.com.rpires.domain;

import anotacao.ColunaTabela;
import anotacao.Tabela;

import java.math.BigDecimal;
//quantidade de produtos que ainda tem no estoque, toda vez que cadastrar um novo produto, devo cadastrar tbm no estoque a quantidade desse determinado produto
//toda vez que uma venda for feita, ir na tabela de estoque e dar baixa na quantidade de produtos que foram vendidos.
@Tabela("TB_ESTOQUE")
public class Estoque {
    @ColunaTabela(dbName = "id", setJavaName = "setId")
    private Long id;
    @ColunaTabela(dbName = "id_produto", setJavaName = "setIdProduto")
    private Produto produto;
    @ColunaTabela(dbName = "id_venda", setJavaName = "setIdVenda")
    private Venda venda;
    @ColunaTabela(dbName = "quantidade_produtos", setJavaName = "setProdutoQuantidade")
    private ProdutoQuantidade produtoQuantidade;
    @ColunaTabela(dbName = "quantidade_total_produtos", setJavaName = "setQuantidadeTotalProdutos")
    private Integer quantidadeTotalProdutos;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Venda getVenda() {
        return venda;
    }

    public void setVenda(Venda venda) {
        this.venda = venda;
    }

    public ProdutoQuantidade getProdutoQuantidade() {
        return produtoQuantidade;
    }

    public void setProdutoQuantidade(ProdutoQuantidade produtoQuantidade) {
        this.produtoQuantidade = produtoQuantidade;
    }

    public Integer getQuantidadeTotalProdutos() {
        return quantidadeTotalProdutos;
    }

    public void setQuantidadeTotalProdutos(Integer quantidadeTotalProdutos) {
        this.quantidadeTotalProdutos = quantidadeTotalProdutos;
    }

    public void adicionarProdutonoEstoque (Produto produto , BigDecimal qtdProdutos) {

    }

    public void removerProdutonoEstoque (Produto produto, BigDecimal qtdProdutos){

    }





}
