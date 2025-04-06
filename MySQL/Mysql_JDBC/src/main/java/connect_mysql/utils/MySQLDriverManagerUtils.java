package connect_mysql.utils;


import java.sql.*;

// 使用单例模式来创建全局唯一的数据库连接，以防止数据库连接过多导致内存溢出
public class MySQLDriverManagerUtils {
    private static Connection connection = null;
    private static final String url ="jdbc:mysql://127.0.0.1:3306/javaDB?characterEncoding=utf8" +
                            "&allowPublicKeyRetrieval=true&useSSL=false";
    private static final String user = "root";
    private static final String passwd = "8888";

    // 使用静态代码块，使得当前的类在加载到 JVM 时就把一系列工具都进行创建
    static {
         try{
             // 1. 使用反射的原理加载 MySQL 的 JDBC 实现类
             Class.forName("com.mysql.cj.jdbc.Driver");
             // 2. 创建连接
             connection = DriverManager.getConnection(url, user, passwd);
         } catch (ClassNotFoundException | SQLException e) {
             throw new RuntimeException(e);
         }
    }

    // 为了保证全局唯一，使用单例模式，禁止类外创建类，所以将构造方法设为 private
    private MySQLDriverManagerUtils(){}

    // 返回一个连接 connection
    public static Connection getConnection(){
        return connection;
    }

    // 关闭连接
    public static void close(ResultSet resultSet, Connection connection, Statement statement) throws SQLException {
        if (resultSet != null){
            resultSet.close();
        }
        if (connection != null){
            connection.close();
        }
        if (statement != null){
            statement.close();
        }
    }
}
