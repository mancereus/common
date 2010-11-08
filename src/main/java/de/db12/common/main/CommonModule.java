package de.db12.common.main;

import javax.sql.DataSource;

import org.mybatis.guice.MyBatisModule;
import org.mybatis.guice.datasource.builtin.UnpooledDataSourceProvider;
import org.mybatis.guice.datasource.helper.JdbcHelper;

import com.google.inject.AbstractModule;
import com.google.inject.Provider;
import com.google.inject.matcher.Matchers;
import com.google.inject.name.Names;

import de.db12.common.db.mybatis.BlogMapper;
import de.db12.common.entity.Blog;
import de.db12.common.guice.log.Slf4jTypeListener;

public class CommonModule extends AbstractModule {
	Class<? extends Provider<DataSource>> dataSourceProviderClass = UnpooledDataSourceProvider.class;

	@Override
	protected void configure() {
		// logger
		bindListener(Matchers.any(), new Slf4jTypeListener());

		// persistence
		// bindConstant()
		// .annotatedWith(com.google.inject.name.Names.named("mybatis.configuration.lazyLoadingEnabled"))
		// .to(true);
		bindConstant().annotatedWith(
				Names.named("mybatis.environment.id")).to("development");
//		bindConstant().annotatedWith(Names.named("JDBC.driver")).to(
//				"com.mysql.jdbc.Driver");
//		bindConstant().annotatedWith(Names.named("JDBC.url")).to(
//				"jdbc:mysql://localhost:3306/QuickRewards");
		bindConstant().annotatedWith(Names.named("JDBC.username")).to(
				"sa");
		bindConstant().annotatedWith(Names.named("JDBC.password")).to(
				"");

		MyBatisModule myBatisModule = (MyBatisModule) new MyBatisModule.Builder()
		.setDataSourceProviderType(dataSourceProviderClass)
		.addMapperClasses(BlogMapper.class)
		.create();
		install(JdbcHelper.HSQLDB_Embedded);
		install(myBatisModule);

	}

}
