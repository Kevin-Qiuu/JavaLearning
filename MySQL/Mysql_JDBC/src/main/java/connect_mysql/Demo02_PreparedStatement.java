package connect_mysql;

import java.sql.*;
import java.text.MessageFormat;
import java.util.Scanner;

public class Demo02_PreparedStatement {
    // 使用 PreparedStatement 语句可以有效防止 SQL 注入攻击
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            // 创建连接和预编译 SQL 语句
            connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/javaDB?characterEncoding=utf8" +
                            "&allowPublicKeyRetrieval=true&useSSL=false",
                    "root", "Qjh20020515");
            String sql = """
                    SELECT
                    b.name  AS battle_name,
                    b.date,
                    b.location,
                    attacker.name  AS attacker_force_name, \s
                    defender.name  AS defender_force_name,\s
                    b.result
                    FROM battles b
                    INNER JOIN forces attacker ON b.attacker_force  = attacker.force_id
                    INNER JOIN forces defender ON b.defender_force  = defender.force_id
                    where b.attacker_force=? or b.defender_force=?;
                    """;
            preparedStatement = connection.prepareStatement(sql);

            // 输入要查询的参数
            Scanner scanner = new Scanner(System.in);
            System.out.print("请输入要查询的攻击方势力id：");
            int attacker_force_id = scanner.nextInt();
            System.out.print("请输入要查询的防御方势力id：");
            int defender_force_id = scanner.nextInt();

            // 设置 sql 语句占位符上的参数
            preparedStatement.setInt(1, attacker_force_id);
            preparedStatement.setInt(2, defender_force_id);

            // 执行查询，获取查询结果
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String battle_name = resultSet.getString("battle_name");
                Date date = resultSet.getDate(2);
                String battle_location = resultSet.getString("b.location");
                String attacker_force_name = resultSet.getString("attacker_force_name");
                String defender_force_name = resultSet.getString("defender_force_name");
                String battle_result = resultSet.getString("b.result");
                System.out.println(MessageFormat.format("战斗名称={0}, 战斗时间={1}, 战斗场所={2}, 攻击方势力={3}, 防御方势力={4}, 战斗结局={5}",
                        battle_name, date, battle_location, attacker_force_name, defender_force_name, battle_result));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            resultSet.close();
            preparedStatement.close();
            connection.close();
        }
    }
}
