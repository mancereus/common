package de.db12.common.guice.test;

import java.util.List;
import java.util.Properties;

import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;

import com.google.inject.Injector;
import com.google.inject.Module;

public abstract class AbstractGuiceTestRunner extends BlockJUnit4ClassRunner {

	protected Injector injector;

	public AbstractGuiceTestRunner(Class<?> klass) throws InitializationError {
		super(klass);

	}

	protected abstract List<Module> createMyBatisModule();

	protected abstract Properties createTestProperties();

	@Override
	protected final Object createTest() throws Exception {
		return this.injector.getInstance(this.getTestClass().getJavaClass());
	}

}
