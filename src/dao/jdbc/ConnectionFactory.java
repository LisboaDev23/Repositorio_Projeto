package dao.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
    /*Para podermos chamar os métodos de conexão, precisamos declarar uma variável estatica para ser chamada dentro
    dos métodos do tipo Connection.
     */
    private static Connection connection;
    //Construtor da classe privada para fazer o singleton. E so poder ser instanciado dentro da própria classe.
    private ConnectionFactory(Connection connection) {

    }

    /*metodo de pegar a conexão, onde se a conexão estiver nula, ela irá iniciar, ou se ela estiver fechada,
    irá iniciar também, senão só irá retornar a conexão.
     */
    public static Connection getConnection() throws SQLException {
        if (connection == null) {
            connection = initConnection();
            return connection;
        } else if (connection.isClosed()) {
            connection = initConnection();
            return connection;
        } else {
            return connection;
        }
    }

    /* Metodo de iniciar a conexão, onde nele vai ser tentada o retorno de uma conexão utilizando o método da classe
    DriveManager chamado getConnection, onde nos parâmetros desse método eu passo o endereço url do meu servidor,
    o meu usuario, e a senha desse servidor, no catch onde será feito o tratamento, vou passar no seu parâmetro um
    SQLException onde vai dizer os erros caso dê, e ele vai lançar um novo RunTimeException, uma exceção de tempo de
    execução, onde no seu paramêtro de instância eu vou passar o SQLException do parâmetro catch.
     */
    private static Connection initConnection() {
        try {
            return DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/exercise_mod_sql","postgres","16833200");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
