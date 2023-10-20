package dao;

import dao.jdbc.ConnectionFactory;
import domain.Cliente;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class ClienteDAO implements IClienteDAO {

    @Override
    public Integer alterar(Cliente cliente) throws Exception {
        Connection conexao = null;
        PreparedStatement pstmt= null;
        try {
            conexao = ConnectionFactory.getConnection();
            String codigoSQL = updateSQL();
            pstmt = conexao.prepareStatement(codigoSQL);
            addParametrosUpdate(pstmt,cliente);
            return pstmt.executeUpdate();

        } catch (Exception e){
            throw e;
        } finally {
            fechandoConexao(conexao,pstmt,null);
        }

    }

    @Override
    public List<Cliente> buscarTodos() throws Exception {
        Connection conexao = null;
        PreparedStatement pstmt= null;
        List<Cliente> clienteList = new ArrayList<>();
        Cliente cliente = null;
        ResultSet resultSet = null;
        try {
            conexao = ConnectionFactory.getConnection();
            String comandoSQL = selectAllSQL();
            pstmt = conexao.prepareStatement(comandoSQL);
            resultSet = pstmt.executeQuery();

            while (resultSet.next()){
                cliente = new Cliente();
                cliente.setId(resultSet.getLong("ID"));
                cliente.setNome(resultSet.getString("NOME"));
                cliente.setCpf(resultSet.getLong("CPF"));
                cliente.setIdProduto(resultSet.getLong("ID_PRODUTO"));
                clienteList.add(cliente);
            } return clienteList;
        } catch (Exception e){
            throw e;
        } finally {
            fechandoConexao(conexao,pstmt,resultSet);
        }

    }

    @Override
    public Integer cadastrar(Cliente cliente) throws Exception {
        Connection connection = null;
        PreparedStatement stm = null;
        try {
            connection = ConnectionFactory.getConnection();
            String sql = insertSQL();
            stm = connection.prepareStatement(sql);
            addParametrosInsert(stm,cliente);
            return stm.executeUpdate();
        } catch(Exception e) {
            throw e;
        } finally {
            fechandoConexao(connection,stm,null);
        }
    }

    @Override
    public Cliente consultar(Long cpf) throws Exception {
        Connection connection = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        Cliente cliente = null;
        try {
            connection = ConnectionFactory.getConnection();
            String sql = selectSQL();
            stm = connection.prepareStatement(sql);
            addParametrosSelect(stm,cpf);
            rs = stm.executeQuery();
            if (rs.next()) {
                cliente = new Cliente();
                cliente.setId(rs.getLong("id"));
                cliente.setNome(rs.getString("nome"));
                cliente.setCpf(rs.getLong("cpf"));
                cliente.setIdade(rs.getInt("idade"));
                cliente.setIdProduto(rs.getLong("id_produto"));

            }
            return cliente;
        } catch(Exception e) {
            throw e;
        } finally {
            fechandoConexao(connection,stm,rs);
        }
    }

    @Override
    public Integer excluir(Cliente cliente) throws Exception {
        Connection connection = null;
        PreparedStatement stm = null;
        try {
            connection = ConnectionFactory.getConnection();
            String sql = deleteSQL();
            stm = connection.prepareStatement(sql);
            addParametrosDelete(stm,cliente);
            return stm.executeUpdate();
        } catch(Exception e) {
            throw e;
        } finally {
            fechandoConexao(connection,stm,null);
        }


    }
    private String insertSQL(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("INSERT INTO CLIENTES (ID, NOME, CPF, IDADE, ID_PRODUTO)");
        stringBuilder.append("VALUES (nextval('SQ_CLIENTES'),?,?,?,?)");
        return stringBuilder.toString();
    }

    private String updateSQL(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("UPDATE CLIENTES");
        stringBuilder.append("SET NOME=?, IDADE=?, ID_PRODUTO=? WHERE CPF=?");
        return stringBuilder.toString();
    }
    private String deleteSQL(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("DELETE FROM CLIENTES WHERE CPF = ?");
        return stringBuilder.toString();
    }
    private String selectSQL(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SELECT*FROM CLIENTES WHERE CPF = ?");
        return stringBuilder.toString();
    }
    private String selectAllSQL(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SELECT*FROM CLIENTES");
        return stringBuilder.toString();
    }
    public void addParametrosInsert(PreparedStatement pstmt, Cliente cliente) throws SQLException {
        pstmt.setString(1, cliente.getNome());
        pstmt.setLong(2,cliente.getCpf());
        pstmt.setInt(3,cliente.getIdade());
        pstmt.setLong(4,cliente.getIdProduto());
    }
    public void addParametrosUpdate(PreparedStatement pstmt, Cliente cliente) throws SQLException{
        pstmt.setString(1, cliente.getNome());
        pstmt.setLong(4,cliente.getCpf());
        pstmt.setInt(2,cliente.getIdade());
        pstmt.setLong(3,cliente.getIdProduto());

    }
    public void addParametrosDelete(PreparedStatement pstmt, Cliente cliente) throws SQLException{
        pstmt.setLong(1,cliente.getCpf());
    }
    public void addParametrosSelect(PreparedStatement pstmt, Long cpf) throws SQLException{
        pstmt.setLong(1,cpf);
    }

    public void fechandoConexao(Connection conexao, PreparedStatement pstmt, ResultSet resultSet) throws SQLException {
        if (conexao!=null && !conexao.isClosed()){
            conexao.close();
        }
        if (pstmt!=null && !pstmt.isClosed()){
            pstmt.close();
        }
        if (resultSet!=null && !resultSet.isClosed()){
            resultSet.close();
        }
    }

}
