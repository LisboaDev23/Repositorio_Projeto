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
        /*necessario definir duas variáveis, uma para chamar os metodos de conexão e outra para chamar os
        metodos de PreparedStatement onde um objeto desse tipo permite chamar metodos onde eu vou ter uma
        interatividade com o SGBD.
         */
        Connection conexao = null;
        PreparedStatement pstmt= null;
        /*
        Depois precisamos tentar, 1- Fazer a conexao com o banco de dados, para isso vou definir que a variavel
        conexão recebe o método getConnection da classe de conexao com o banco de dados ConnectionFactory
        2- Necessario definir uma string onde ela vai ser responsável por representar o codigo SQL que vai ser
        enviado para execução, desse modo essa String vai receber o método que define o comando em SQL, seja ele
        UPDATE,INSERT, ALTER, CREATE e etc.
        3- Vou definir que a variavel PreparedStatement recebe o metodo prepareStatement do objeto conexao, onde esse
        metodo vai compilar a String do comando SQL feito anteriormente, então eu passo essa string como parametro desse
        metodo do prepareStatement.
        4- Necessario um metodo onde vou inserir os parâmetros dos sinais ? inseridos no codigo SQL.]
        5- Criado esse método, nele vamos adicionar esses parâmetros passando no parâmetro do método a variável que
        foi responsável por compilar o codigo, e o cliente que está sendo alterado. Por fim o retorno desse método
        é a chamada do método executeUpdate da variável PreparedStatement, onde é retornado um Integer da quantidade
        de registros.
        6- No catch onde deveria ser realizado o tratamento, nesse caso só iremos lançar um execeção.
        7- No finally onde será sempre executado, fechamos a conexão usando um método fechandoConexão.
         */
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
        /*nesse metodo que retorna uma String eu instancio uma variavel do tipo StringBuilder, essa Classe é responsável por
        criar e manipular dados de String dinamicamente, com isso utilizamos o metodo append, onde no seu
        parâmetro eu vou passar em forma de string o comando SQL do metodo, e esse metodo retorna um metodo do
        stringBuilder chamado toString para o comando ser transformando em String.
         */
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("UPDATE CLIENTES");
        stringBuilder.append(" SET NOME=?, IDADE=?, ID_PRODUTO=? WHERE CPF=?");
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
        /*
        Nesse metodo onde vamos inserir os valores em aberto no codigo SQL, ele vai pedir nos parâmetros
        uma variável PreparedStatement onde depois de compilada, precisa ter seus dados inseridos, e de quem
        são esses dados? De um respectivo cliente. Dessa forma para adicionarmos esses dados, precisamos chamar
        o metodo setString ou setLong dependendo do tipo de variável que for pegada do cliente, esse método
        é da variavel PreparedStatement, nesse metodo vamos passar nos seus parâmetros a posição que será preenchida
        no código SQL, e depois oque será alterado nessa posição, nesse casso aqui na posição 1, vamos pegar o
        nome do cliente. Atentar-se na sequência que foi escrita no código SQL para não ocorrer divergências.
         */
        pstmt.setString(1, cliente.getNome());
        pstmt.setInt(2,cliente.getIdade());
        pstmt.setLong(3,cliente.getIdProduto());
        pstmt.setLong(4,cliente.getCpf());

    }
    public void addParametrosDelete(PreparedStatement pstmt, Cliente cliente) throws SQLException{
        pstmt.setLong(1,cliente.getCpf());
    }
    public void addParametrosSelect(PreparedStatement pstmt, Long cpf) throws SQLException{
        pstmt.setLong(1,cpf);
    }

    public void fechandoConexao(Connection conexao, PreparedStatement pstmt, ResultSet resultSet) throws SQLException {
        /*
        Nesse método, ele não terá nenhum tipo de retorno, e para uma conexão ser fechada, ela irá precisar
        consequentemente de uma conexão, de um PreparedStatement, e de um Resultado de retorno do comando
        SQL, nesse último nem tanto, podendo defini-lo como nulo em algumas ocasiões, de qualquer forma ele
        irá fechar a conexão.
         */
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
