package org.shop.mapper.typeHandler;

import oracle.jdbc.OracleArray;
import oracle.jdbc.OracleConnection;
import oracle.jdbc.OracleStruct;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;
import org.shop.domain.entity.ProductInfoImage;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ProductInfoImageTypeHandler implements TypeHandler<List<ProductInfoImage>> {

    @Override
    public void setParameter(PreparedStatement ps, int i, List<ProductInfoImage> parameter, JdbcType jdbcType) throws SQLException {
        if(parameter != null && !parameter.isEmpty()){
            OracleConnection oracleConn = ps.getConnection().unwrap(OracleConnection.class);
            OracleStruct[] structArray = new OracleStruct[parameter.size()];

            for(int idx = 0; idx < parameter.size(); idx++){
                ProductInfoImage infoImage = parameter.get(idx);

                Object[] infoImageAttributes = {
                        infoImage.getProductId(),
                        infoImage.getImageName()
                };

                OracleStruct struct = (OracleStruct) oracleConn.createStruct("PRODUCT_INFO_IMAGE_OBJ", infoImageAttributes);
                structArray[idx] = struct;
            }

            OracleArray array = (OracleArray) oracleConn.createOracleArray("PRODUCT_INFO_IMAGE_OBJ_LIST", structArray);
            ps.setArray(i, array);
        }
    }

    @Override
    public List<ProductInfoImage> getResult(ResultSet rs, String columnName) throws SQLException {
        return null;
    }

    @Override
    public List<ProductInfoImage> getResult(ResultSet rs, int columnIndex) throws SQLException {
        return null;
    }

    @Override
    public List<ProductInfoImage> getResult(CallableStatement cs, int columnIndex) throws SQLException {
        return null;
    }
}
