package org.shop.mapper.typeHandler;

import oracle.jdbc.OracleArray;
import oracle.jdbc.OracleConnection;
import oracle.jdbc.OracleStruct;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;
import org.shop.domain.entity.ProductOption;

import java.sql.*;
import java.util.List;

public class ProductOptionTypeHandler implements TypeHandler<List<ProductOption>> {

    @Override
    public void setParameter(PreparedStatement ps, int i, List<ProductOption> parameter, JdbcType jdbcType) throws SQLException {
        if(parameter != null && !parameter.isEmpty()){
            OracleConnection oracleConn = ps.getConnection().unwrap(OracleConnection.class);
            OracleStruct[] structArray = new OracleStruct[parameter.size()];

            for(int idx = 0; idx < parameter.size(); idx++) {
                ProductOption option = parameter.get(idx);

                Object[] optionAttributes = {
                        option.getProductId(),
                        option.getProductSize(),
                        option.getProductColor(),
                        option.getStock(),
                        option.isOpen()
                };

                OracleStruct struct = (OracleStruct) oracleConn.createStruct("PRODUCT_OPTION_OBJ", optionAttributes);
                structArray[idx] = struct;
            }

            OracleArray array = (OracleArray) oracleConn.createOracleArray("PRODUCT_OPTION_OBJ_LIST", structArray);
            ps.setArray(i, array);
        }else
            ps.setNull(i, Types.ARRAY);
    }

    @Override
    public List<ProductOption> getResult(ResultSet rs, String columnName) throws SQLException {
        return null;
    }

    @Override
    public List<ProductOption> getResult(ResultSet rs, int columnIndex) throws SQLException {
        return null;
    }

    @Override
    public List<ProductOption> getResult(CallableStatement cs, int columnIndex) throws SQLException {
        return null;
    }
}
