package org.shop.mapper.typeHandler;

import jdk.internal.org.objectweb.asm.Type;
import oracle.jdbc.OracleArray;
import oracle.jdbc.OracleConnection;
import oracle.jdbc.OracleStruct;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;
import org.shop.domain.entity.ProductThumbnail;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ProductThumbnailTypeHandler implements TypeHandler<List<ProductThumbnail>> {

    @Override
    public void setParameter(PreparedStatement ps, int i, List<ProductThumbnail> parameter, JdbcType jdbcType) throws SQLException {
        if(parameter != null && !parameter.isEmpty()){
            OracleConnection oracleConn = ps.getConnection().unwrap(OracleConnection.class);
            OracleStruct[] structArray = new OracleStruct[parameter.size()];

            for(int idx = 0; idx < parameter.size(); idx++){
                ProductThumbnail thumbnail = parameter.get(idx);

                Object[] thumbnailAttributes = {
                        thumbnail.getProductId(),
                        thumbnail.getImageName()
                };

                OracleStruct struct = (OracleStruct) oracleConn.createStruct("PRODUCT_THUMBNAIL_OBJ", thumbnailAttributes);
                structArray[idx] = struct;
            }

            OracleArray array = (OracleArray) oracleConn.createOracleArray("PRODUCT_THUMBNAIL_OBJ_LIST", structArray);
            ps.setArray(i, array);
        }else
            ps.setNull(i, Type.ARRAY);
    }

    @Override
    public List<ProductThumbnail> getResult(ResultSet rs, String columnName) throws SQLException {
        return null;
    }

    @Override
    public List<ProductThumbnail> getResult(ResultSet rs, int columnIndex) throws SQLException {
        return null;
    }

    @Override
    public List<ProductThumbnail> getResult(CallableStatement cs, int columnIndex) throws SQLException {
        return null;
    }
}
