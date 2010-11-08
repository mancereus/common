package de.db12.common.main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.hsqldb.Server;

public class HSQLDBMain {

	public static void main(String[] args) throws ClassNotFoundException,
			SQLException {

		// 'Server' is a class of HSQLDB representing
		// the database server
		Server hsqlServer = null;
		try {
			hsqlServer = new Server();

			// HSQLDB prints out a lot of informations when
			// starting and closing, which we don't need now.
			// Normally you should point the setLogWriter
			// to some Writer object that could store the logs.
			hsqlServer.setLogWriter(null);
			hsqlServer.setSilent(true);

			// The actual database will be named 'xdb' and its
			// settings and data will be stored in files
			// testdb.properties and testdb.script
			hsqlServer.setDatabaseName(0, "test-db");
			hsqlServer.setDatabasePath(0, "file:testdb");

			// Start the database!
			hsqlServer.start();

			Connection connection = null;
			try {
				Class.forName("org.hsqldb.jdbcDriver");
				connection = DriverManager.getConnection(
						"jdbc:hsqldb:hsql://localhost/test-db", "sa", "");

				// Here we run a few SQL statements to see if
				// everything is working.
				// We first drop an existing 'testtable' (supposing
				// it was there from the previous run), create it
				// once again, insert some data and then read it
				// with SELECT query.
				// connection.prepareStatement("drop table testtable;").execute();
				connection.prepareStatement(
						"create table contact ( id INTEGER, "
								+ "name VARCHAR(255) );").execute();
				connection.prepareStatement(
						"insert into contact(id, name) "
								+ "values (1, 'testvalue');").execute();

			} finally {
				// Closing the connection
				if (connection != null) {
					connection.close();
				}

			}
		} finally {
			// Closing the server
			if (hsqlServer != null) {
				hsqlServer.stop();
			}
		}
	}
}