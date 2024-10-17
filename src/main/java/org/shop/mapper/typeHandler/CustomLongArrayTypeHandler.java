package org.shop.mapper.typeHandler;

import oracle.jdbc.OracleConnection;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CustomLongArrayTypeHandler implements TypeHandler<List<Long>> {

    @Override
    public void setParameter(PreparedStatement ps, int i, List<Long> parameter, JdbcType jdbcType) throws SQLException {
        if (parameter != null && !parameter.isEmpty()) {
            OracleConnection oracleConn = ps.getConnection().unwrap(OracleConnection.class);
            Long[] arrayData = parameter.toArray(new Long[0]);
            Array oracleArray = oracleConn.createOracleArray("SYS.ODCINUMBERLIST", arrayData);
            ps.setArray(i, oracleArray);
        } else {
            ps.setNull(i, Types.ARRAY);
        }
    }

    @Override
    public List<Long> getResult(ResultSet rs, String columnName) throws SQLException {
        return null;
    }

    @Override
    public List<Long> getResult(ResultSet rs, int columnIndex) throws SQLException {
        return null;
    }

    @Override
    public List<Long> getResult(CallableStatement cs, int columnIndex) throws SQLException {
        return null;
    }

}
