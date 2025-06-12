package com.kevinqiu.lotterysystem.dao.handler;

import cn.hutool.crypto.SecureUtil;
import com.kevinqiu.lotterysystem.dao.dataobject.Encrypt;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 这个类用于将 SQL 语句中指定的类型转换成另一个类型的类
 * 通过指定 SQL 中的索引未知，在向数据库查询（加密）和从数据库获取（解密）数据时进行转化
 */
@MappedTypes(Encrypt.class) // 指定的类型
@MappedJdbcTypes(JdbcType.VARCHAR) // 转换以后的类型
public class EncryptTypeHandler extends BaseTypeHandler<Encrypt> {

    private final byte[] KEY = "12345678abcdefgh".getBytes();

    /**
     * 设置参数
     * @param ps SQL 预编译对象
     * @param i 需要赋值的索引位置
     * @param parameter 原本位置 i 指定的类型
     * @param jdbcType JDBC 类型
     */
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Encrypt parameter, JdbcType jdbcType) throws SQLException {
        if (parameter == null || parameter.getValue() == null) {
            ps.setString(i, "");
            return;
        }

        // 加密 Encrypt 数据
        String encryptRet = SecureUtil.aes(KEY).encryptHex(parameter.getValue());
        ps.setString(i, encryptRet);

    }

    /**
     * 根据列名从数据库中获取加密数据，然后进行类型转换
     * @param rs SQL 预编译对象
     * @param columnName 列名
     * @return 返回指定的类型
     */
    @Override
    public Encrypt getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String decryptRet = SecureUtil.aes(KEY).decryptStr(rs.getBytes(columnName));
        return new Encrypt(decryptRet);
    }

    @Override
    public Encrypt getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String decryptRet = SecureUtil.aes(KEY).decryptStr(rs.getBytes(columnIndex));
        return new Encrypt(decryptRet);
    }

    @Override
    public Encrypt getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String decryptRet = SecureUtil.aes(KEY).decryptStr(cs.getBytes(columnIndex));
        return new Encrypt(decryptRet);
    }
}
