package de.db12.common.db.mybatis;

import javax.sql.DataSource;

import org.mybatis.guice.MyBatisModule;
import org.mybatis.guice.datasource.builtin.PooledDataSourceProvider;

import com.google.inject.AbstractModule;
import com.google.inject.Provider;

import de.db12.common.db.entity.Address;
import de.db12.common.db.entity.Blog;
import de.db12.common.db.entity.Contact;
import de.db12.common.db.entity.Profile;
import de.db12.common.db.mapper.BlogMapper;
import de.db12.common.db.mapper.ContactMapper;
import de.db12.common.db.mapper.ProfileMapper;

public class ConfigureMybatisModule extends AbstractModule {
	Class<? extends Provider<DataSource>> dataSourceProviderClass = PooledDataSourceProvider.class;

	@Override
	protected void configure() {
		// //TestCase errors with lazy loading
		// bindConstant().annotatedWith(
		// com.google.inject.name.Names
		// .named("mybatis.configuration.lazyLoadingEnabled")).to(
		// true);

		MyBatisModule myBatisModule = (MyBatisModule) new MyBatisModule.Builder()
				.setDataSourceProviderType(dataSourceProviderClass)
				.addMapperClasses(ContactMapper.class)
				.addMapperClasses(BlogMapper.class)
				.addMapperClasses(ProfileMapper.class)
				.addAlias("Contact", Contact.class)
				.addAlias("Blog", Blog.class)
				.addAlias("Profile", Profile.class)
				.addTypeHandler(CustomType.class, CustomLongTypeHandler.class)
				.addTypeHandler(Address.class, AddressTypeHandler.class)
				.addInterceptorsClasses(CountUpdateInterceptor.class).create();
		install(myBatisModule);

	}
}
