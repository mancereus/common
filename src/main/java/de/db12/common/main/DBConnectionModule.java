package de.db12.common.main;

import javax.sql.DataSource;

import org.mybatis.guice.datasource.builtin.PooledDataSourceProvider;
import org.mybatis.guice.datasource.helper.JdbcHelper;

import com.google.inject.AbstractModule;
import com.google.inject.Provider;
import com.google.inject.name.Names;

public class DBConnectionModule extends AbstractModule {
	Class<? extends Provider<DataSource>> dataSourceProviderClass = PooledDataSourceProvider.class;

	@Override
	protected void configure() {
		// persistence
		bindConstant().annotatedWith(Names.named("mybatis.environment.id")).to(
				"development");
		// bindConstant().annotatedWith(Names.named("JDBC.driver")).to(
		// "com.mysql.jdbc.Driver");
		// bindConstant().annotatedWith(Names.named("JDBC.url")).to(
		// "jdbc:mysql://localhost:3306/QuickRewards");
		bindConstant().annotatedWith(Names.named("JDBC.schema")).to("test-db");
		bindConstant().annotatedWith(Names.named("JDBC.username")).to("sa");
		bindConstant().annotatedWith(Names.named("JDBC.password")).to("");
		install(JdbcHelper.HSQLDB_Server);
	}
}
