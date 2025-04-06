package connect_mysql.utils;

import java.sql.*;
import java.text.MessageFormat;

public class Demo01_userUtils {
    public static void main(String[] args) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = MySQLDataSourceUtils.getConnection();
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
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();
            while (resultSet.next()){
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
            try {
                MySQLDataSourceUtils.close(connection, statement, resultSet);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public static void main1(String[] args){
        Connection connection = MySQLDriverManagerUtils.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

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

        try {
            // 创建一个 statement 来执行 sql 语句
            preparedStatement = connection.prepareStatement(sql);
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
            try {
                MySQLDriverManagerUtils.close(resultSet,connection, preparedStatement);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }




    }

}
