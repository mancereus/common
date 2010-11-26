package de.scoopgmbh.bms.db.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SupervisorUtil {

	private Connection con = null;
	
	public SupervisorUtil(Connection con) {
		this.con = con;
	}
	
	public void deleteGlobalSequence() {
		try {
			Statement stmt  = con.createStatement();
			stmt.executeUpdate("DELETE FROM SEQUENCE");
			con.commit();
		} catch (SQLException e) {
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public long getCurrentIdValueOfSequenceGlobal() {
		long ret = 0;
		try {
			Statement stmt  = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT NEXTIDINSEQ FROM SEQUENCE WHERE SEQNAME = 'GLOABAL'");
			if (rs.next()) {
				ret = rs.getLong(1);
			}
			con.commit();
		} catch (SQLException e) {
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return ret;
	}
	
	public int getRecordCount(String tableName) {
		int ret = 0;
		
		if (tableName==null || tableName.length()==0) return ret;
		ResultSet rs = null;
		
		try {
			Statement stmt  = con.createStatement();
			rs = stmt.executeQuery("SELECT COUNT(*) FROM " + tableName);
			if (rs.next())
				ret = rs.getInt(1);
			else
				ret = -1;
			con.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			ret = 0;
		}
		return ret;
	}
	
	public void execDML(StringBuilder sb) throws SQLException {
		Statement stmt  = con.createStatement();
		stmt.executeQuery(sb.toString());
		con.commit();
	}
	
	public void execBatch(StringBuilder sb) throws SQLException {
		Statement stmt  = con.createStatement();
		stmt.addBatch(sb.toString());
		stmt.executeBatch();
		con.commit();
	}
}
