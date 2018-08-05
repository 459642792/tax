package com.blueteam.base.help.mybatis;

import com.blueteam.base.constant.Enums;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * mybatis 枚举 处理
 *
 * @author libra
 */
public class EnumTypeHandler extends BaseTypeHandler<Enums.Enum> {
    private Class<Enums.Enum> type;

    public EnumTypeHandler(Class<Enums.Enum> type) {
        if (type == null)
            throw new IllegalArgumentException("Type argument cannot be null");
        this.type = type;
    }

    private Enums.Enum convert(int status) {
        Enums.Enum[] objs = type.getEnumConstants();
        for (Enums.Enum em : objs) {
            if (em.getValue() == status) {
                return em;
            }
        }
        return null;
    }


    public Enums.Enum getNullableResult(ResultSet rs, String columnName)
            throws SQLException {
        return convert(rs.getInt(columnName));
    }


    public Enums.Enum getNullableResult(ResultSet rs, int columnIndex)
            throws SQLException {
        return convert(rs.getInt(columnIndex));
    }


    public Enums.Enum getNullableResult(CallableStatement cs, int columnIndex)
            throws SQLException {
        return convert(cs.getInt(columnIndex));
    }


    public void setNonNullParameter(PreparedStatement ps, int i,
                                    Enums.Enum enumObj, JdbcType jdbcType) throws SQLException {
        // baseTypeHandler已经帮我们做了parameter的null判断
        ps.setInt(i, enumObj.getValue());

    }

}
