package de.db12.common.db.mapper;

public class SQLProvider {

	public String getDeleteSQL(String table, int id) {
		return "delete from " + table + " where id = " + id;

	}
}
