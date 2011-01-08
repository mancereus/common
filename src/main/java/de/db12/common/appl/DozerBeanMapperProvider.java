package de.db12.common.appl;

import org.dozer.DozerBeanMapperSingletonWrapper;
import org.dozer.Mapper;

import com.google.inject.Provider;

public class DozerBeanMapperProvider implements Provider<Mapper> {

	@Override
	public Mapper get() {
		return DozerBeanMapperSingletonWrapper.getInstance();
	}

}
