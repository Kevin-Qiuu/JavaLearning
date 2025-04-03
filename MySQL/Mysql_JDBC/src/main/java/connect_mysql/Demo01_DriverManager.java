package connect_mysql;

import java.sql.*;
import java.text.MessageFormat;

public class Demo01_DriverManager {
    public static void main(String[] args) {
        ResultSet resultSet = null;
        Statement statement = null;
        Connection connection = null;
        try {
            // 1. 加载数据库厂商提供的驱动
            Class.forName("com.mysql.cj.jdbc.Driver");
            // 2. 获取数据库的连接
            connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/javaDB?characterEncoding=utf8" +
                            "&allowPublicKeyRetrieval=true&useSSL=false",
                    "root", "Qjh20020515");
            // 3. 创建 Statement 对象
            statement = connection.createStatement();
            // 4. 定义 SQL 语句
            String sql = """
                    SELECT\s
                        b.name  AS battle_name,
                        b.date,\s
                        b.location,\s
                        attacker.name  AS attacker_force_name,  -- 攻击方势力名\s
                        defender.name  AS defender_force_name,  -- 防守方势力名\s
                        b.result \s
                    FROM battles b\s
                    INNER JOIN forces attacker ON b.attacker_force  = attacker.force_id   -- 明确攻击方关联\s
                    INNER JOIN forces defender ON b.defender_force  = defender.force_id;  -- 明确防守方关联\s
                    """;
            // 5. 执行 SQL 语句
            resultSet = statement.executeQuery(sql);// 执行查询语句
            // 6. 遍历结果，获取数据行
            // 可以将 resultSet 视为一个迭代器
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
//            statement.executeUpdate(sql); // 执行更新语句
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                // 7. 释放资源
                resultSet.close();
                statement.close();
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
