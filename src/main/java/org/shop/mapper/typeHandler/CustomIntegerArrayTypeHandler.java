package org.shop.mapper.typeHandler;

import oracle.jdbc.OracleConnection;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CustomIntegerArrayTypeHandler implements TypeHandler<List<Integer>> {

    @Override
    public void setParameter(PreparedStatement ps, int i, List<Integer> parameter, JdbcType jdbcType) throws SQLException {
        if (parameter != null && !parameter.isEmpty()) {
            OracleConnection oracleConn = ps.getConnection().unwrap(OracleConnection.class);
            Integer[] arrayData = parameter.toArray(new Integer[0]);
            Array oracleArray = oracleConn.createOracleArray("SYS.ODCINUMBERLIST", arrayData);
            ps.setArray(i, oracleArray);
        } else {
            ps.setNull(i, Types.ARRAY);
        }
    }

    @Override
    public List<Integer> getResult(ResultSet rs, String columnName) throws SQLException {
        return null;
    }

    @Override
    public List<Integer> getResult(ResultSet rs, int columnIndex) throws SQLException {
        return null;
    }

    @Override
    public List<Integer> getResult(CallableStatement cs, int columnIndex) throws SQLException {
        return null;
    }
}
