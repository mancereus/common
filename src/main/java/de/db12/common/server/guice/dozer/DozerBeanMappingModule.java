package de.db12.common.server.guice.dozer;

import org.dozer.Mapper;
import com.google.inject.Binder;
import com.google.inject.Module;

public class DozerBeanMappingModule implements Module {

	@Override
	public void configure(Binder binder) {
		binder.bind(Mapper.class).toProvider(DozerBeanMapperProvider.class);

	}

}
