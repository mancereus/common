package de.scoopgmbh.common.appl;

import java.util.List;

import com.google.inject.Guice;
import com.google.inject.Injector;

import de.scoopgmbh.common.db.data.Product;
import de.scoopgmbh.common.guice.log.LoggingModule;
import de.scoopgmbh.common.service.ProductService;

public class GuiceMain {
	public static void main(String[] args) {

		Injector injector = Guice.createInjector(new DozerBeanMappingModule(),
				new EbeanConfigurationModule(), new LoggingModule(),
				new SetupModule());
		ProductService service = injector.getInstance(ProductService.class);
		List<Product> products = service.findAllProducts();
		for (Product product : products) {
			System.out.println(product);
		}
	}
}
