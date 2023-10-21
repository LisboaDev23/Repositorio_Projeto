package dao;

import dao.jdbc.ConnectionFactory;
import domain.Produto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO implements IProdutoDAO {
    @Override
    public Integer cadastrar(Produto produto) throws Exception {
        Connection conexao = null;
        PreparedStatement pstmt = null;
        try{
            //primeiro passo fazer a conexão com o banco
            conexao = ConnectionFactory.getConnection();
            //segundo passo, denominar uma String onde vai receber o comando SQL
            String comandoSQL= insertSQL();
            //terceiro passo compilar o codigo SQL
            pstmt = conexao.prepareStatement(comandoSQL);
            //quarto passo é adicionar os parâmetros de inserção no banco de dados para que seja trocado os dados que forem inseridos aqui, mudarem lá no banco
            addParametrosInsert(pstmt,produto);
            //quinto passo executar o comando onde retorna o numero de elementos cadastrados
            return pstmt.executeUpdate();

        } catch (Exception e) {
            throw e;
        } finally {
            fechandoConexaoBD(conexao,pstmt,null);
        }
    }

    @Override
    public Produto consultar(Long codigo) throws Exception {
        Connection conexao = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        Produto produto = null;
        try {
            conexao = ConnectionFactory.getConnection();
            String comandoSql = selectSQL();
            pstmt = conexao.prepareStatement(comandoSql);
            addParametrosSelect(pstmt,codigo);
            resultSet = pstmt.executeQuery();
            if (resultSet.next()){
                produto = new Produto();
                produto.setId(resultSet.getLong("ID"));
                produto.setNome(resultSet.getString("NOME"));
                produto.setCodigo(resultSet.getLong("CODIGO"));
                produto.setPreco(resultSet.getLong("PRECO"));
                produto.setQtd_estoque(resultSet.getInt("QTD_ESTOQUE"));
            }
            return produto;
        } catch (Exception e) {
            throw e;
        } finally {
            fechandoConexaoBD(conexao,pstmt,resultSet);
        }
    }

    @Override
    public List<Produto> buscarTodos() throws Exception {
        Connection conexao = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null; //retorno do banco de dados que contêm as informações do meu SELECT.
        Produto produto = null;
        List<Produto> produtoList = new ArrayList<>();
        try {
            conexao = ConnectionFactory.getConnection();
            String comandoSql = selectAllSQL();
            pstmt = conexao.prepareStatement(comandoSql);
            //depois de ter compilado o codigo, devo unir o comando no banco usando a variável PreparedStatement
            resultSet = pstmt.executeQuery();
            while (resultSet.next()){
                produto = new Produto();
                produto.setId(resultSet.getLong("ID"));
                produto.setNome(resultSet.getString("NOME"));
                produto.setCodigo(resultSet.getLong("CODIGO"));
                produtoList.add(produto);
            }
            return produtoList;
        } catch (Exception e) {
            throw e;
        } finally {
            fechandoConexaoBD(conexao,pstmt,resultSet);
        }
    }



    @Override
    public Integer alterar(Produto produto) throws Exception {
        Connection conexao = null;
        PreparedStatement pstmt = null;
        try {
            conexao = ConnectionFactory.getConnection();
            String comandoSql = updateSQL();
            pstmt = conexao.prepareStatement(comandoSql);
            //depois de ter compílado o codigo, devo inseri-lo no banco usando a variável PreparedStatement
            addParametrosUpdate(pstmt,produto);
            return pstmt.executeUpdate();

        } catch (Exception e) {
            throw e;
        } finally {
            fechandoConexaoBD(conexao,pstmt,null);
        }
    }



    @Override
    public Integer excluir(Produto produto) throws Exception {
        Connection conexao = null;
        PreparedStatement pstmt = null;
        try {
            conexao = ConnectionFactory.getConnection();
            String comandoSql = deleteSQL();
            pstmt = conexao.prepareStatement(comandoSql);
            //depois de ter compílado o codigo, devo inseri-lo no banco usando a variável PreparedStatement
            addParametrosDelete(pstmt,produto);
            pstmt.setLong(1,produto.getCodigo());
            return pstmt.executeUpdate();

        } catch (Exception e) {
            throw e;
        } finally {
            fechandoConexaoBD(conexao,pstmt,null);
        }
    }


    public void fechandoConexaoBD(Connection conexao, PreparedStatement pstmt, ResultSet rst){
        try{
            if (conexao!=null && !conexao.isClosed()){
                conexao.close();
            }
            if (rst!=null && !rst.isClosed()){
                rst.close();
            }
            if (pstmt!=null && !pstmt.isClosed()){
                pstmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void addParametrosDelete(PreparedStatement pstmt,Produto produto ) throws SQLException {
        pstmt.setLong(1,produto.getCodigo());
    }
    private void addParametrosUpdate(PreparedStatement pstmt, Produto produto ) throws SQLException {
        pstmt.setString(1, produto.getNome());
        pstmt.setLong(2,produto.getPreco());
        pstmt.setInt(3,produto.getQtd_estoque());
        pstmt.setLong(4,produto.getCodigo());
    }

    private void addParametrosSelect(PreparedStatement pstmt, Long codigo) throws SQLException {
        pstmt.setLong(1,codigo);
    }

    public void addParametrosInsert(PreparedStatement pstmt, Produto produto) throws SQLException {
        pstmt.setString(1,produto.getNome());
        pstmt.setLong(2,produto.getCodigo());
        pstmt.setLong(3,produto.getPreco());
        pstmt.setInt(4,produto.getQtd_estoque());
    }

    private String updateSQL() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("UPDATE PRODUTOS ");
        stringBuilder.append("SET NOME=?, PRECO=?, QTD_ESTOQUE=? WHERE CODIGO=?");
        return stringBuilder.toString();
    }
    public String selectSQL (){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SELECT*FROM PRODUTOS WHERE CODIGO = ?");
        return stringBuilder.toString();
    }

    public String insertSQL(){
        StringBuilder stgBuilder = new StringBuilder();
        stgBuilder.append("INSERT INTO PRODUTOS (ID, NOME, CODIGO, PRECO, QTD_ESTOQUE) VALUES (nextval('SQ_PRODUTOS'),?,?,?,?)");
        return stgBuilder.toString();
    }

    private String deleteSQL() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("DELETE FROM PRODUTOS WHERE CODIGO = ?");
        return stringBuilder.toString();
    }

    private String selectAllSQL() {
        StringBuilder stringBuilder  = new StringBuilder();
        stringBuilder.append("SELECT*FROM PRODUTOS");
        return stringBuilder.toString();
    }
}
