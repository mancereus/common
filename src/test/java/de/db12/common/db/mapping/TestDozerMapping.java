package de.db12.common.db.mapping;

import junit.framework.Assert;

import org.dozer.DozerBeanMapperSingletonWrapper;
import org.junit.Test;

import de.db12.common.db.data.Car;
import de.db12.common.db.dto.CarDto;

public class TestDozerMapping {

	@Test
	public void test() {
		Car car = new Car();
		car.setLicense("testest");
		car.setDriver("23232");
		CarDto dto = DozerBeanMapperSingletonWrapper.getInstance().map(car,
				CarDto.class);
		Assert.assertNotNull(dto);
	}
}
