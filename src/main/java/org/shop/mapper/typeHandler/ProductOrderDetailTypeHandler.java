package org.shop.mapper.typeHandler;

import oracle.jdbc.OracleArray;
import oracle.jdbc.OracleConnection;
import oracle.jdbc.OracleStruct;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;
import org.shop.domain.entity.ProductOrderDetail;

import java.sql.*;
import java.util.List;

public class ProductOrderDetailTypeHandler implements TypeHandler<List<ProductOrderDetail>> {

    @Override
    public void setParameter(PreparedStatement ps, int i, List<ProductOrderDetail> parameter, JdbcType jdbcType) throws SQLException {
        if (parameter != null && !parameter.isEmpty()) {
            OracleConnection oracleConn = ps.getConnection().unwrap(OracleConnection.class);

            OracleStruct[] structArray = new OracleStruct[parameter.size()];

            for(int idx = 0; idx < parameter.size(); idx++){
                ProductOrderDetail orderDetail = parameter.get(idx);

                Object[] orderDetailAttributes = {
                        orderDetail.getProductOptionId(),
                        orderDetail.getProductId(),
                        orderDetail.getOrderDetailCount(),
                        orderDetail.getOrderDetailPrice()
                };

                OracleStruct struct = (OracleStruct) oracleConn.createStruct("ORDER_DETAIL_OBJ", orderDetailAttributes);
                structArray[idx] = struct;
            }

            OracleArray array = (OracleArray) oracleConn.createOracleArray("ORDER_DETAIL_OBJ_LIST", structArray);
            ps.setArray(i, array);


            /*OracleConnection oracleConn = ps.getConnection().unwrap(OracleConnection.class);
            Struct[] structArray = new Struct[parameter.size()];

            for(int idx = 0; idx < parameter.size(); idx++) {
                ProductOrderDetail orderDetail = parameter.get(idx);

                Object[] orderDetailAttributes = {
                        orderDetail.getProductOptionId(),
                        orderDetail.getProductId(),
                        orderDetail.getOrderDetailCount(),
                        orderDetail.getOrderDetailPrice()
                };

                structArray[idx] = oracleConn.createStruct("ORDER_DETAIL_OBJ", orderDetailAttributes);
            }

            Array oracleArray = oracleConn.createOracleArray("ORDER_DETAIL_OBJ", structArray);
            ps.setArray(i, oracleArray);*/
        } else {
            ps.setNull(i, Types.ARRAY);
        }
    }

    @Override
    public List<ProductOrderDetail> getResult(ResultSet rs, String columnName) throws SQLException {
        return null;
    }

    @Override
    public List<ProductOrderDetail> getResult(ResultSet rs, int columnIndex) throws SQLException {
        return null;
    }

    @Override
    public List<ProductOrderDetail> getResult(CallableStatement cs, int columnIndex) throws SQLException {
        return null;
    }
}
