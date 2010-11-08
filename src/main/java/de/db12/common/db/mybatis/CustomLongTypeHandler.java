package de.db12.common.db.mybatis;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

public class CustomLongTypeHandler extends BaseTypeHandler {

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i,
			Object parameter, JdbcType jdbcType) throws SQLException {
		if (jdbcType == JdbcType.TIMESTAMP) {
			if (parameter instanceof CustomType) {
				ps.setTimestamp(i,
						new Timestamp(((CustomType) parameter).getValue()));
			}
		}
	}

	@Override
	public Object getNullableResult(ResultSet rs, String columnName)
			throws SQLException {

		Object value = rs.getObject(columnName);
		if (value instanceof Timestamp) {
			CustomType t = new CustomType();
			t.setValue(((Timestamp) value).getTime());
			return t;
		}
		return null;
	}

	@Override
	public Object getNullableResult(CallableStatement cs, int columnIndex)
			throws SQLException {
		Object value = cs.getObject(columnIndex);
		if (value instanceof Timestamp) {
			CustomType t = new CustomType();
			t.setValue(((Timestamp) value).getTime());
			return t;
		}
		return null;
	}

}
