package de.db12.common.guice;

import org.dozer.Mapper;
import com.google.inject.Binder;
import com.google.inject.Module;

public class DozerBeanMappingModule implements Module {

	@Override
	public void configure(Binder binder) {
		binder.bind(Mapper.class).toProvider(DozerBeanMapperProvider.class);

	}

}
