package connect_mysql.utils;

import com.mysql.cj.jdbc.MysqlDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

// 同样使用单例模式创建全局唯一的 MySQLDatasource
public class MySQLDataSourceUtils {
    private static MysqlDataSource dataSource = null;
    private static final String url ="jdbc:mysql://127.0.0.1:3306/javaDB?characterEncoding=utf8" +
                            "&allowPublicKeyRetrieval=true&useSSL=false";
    private static final String user = "root";
    private static final String passwd = "8888";


    static {
        try {
            dataSource = new MysqlDataSource();
            dataSource.setURL(url);
            dataSource.setUser(user);
            dataSource.setPassword(passwd);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    private MySQLDataSourceUtils(){}

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public static void close(Connection connection, Statement statement, ResultSet resultSet) throws SQLException {
        if (resultSet != null)
            resultSet.close();
        if (connection != null)
            connection.close();
        if (statement != null)
            statement.close();
    }

}
