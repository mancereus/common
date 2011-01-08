package de.db12.common.appl;

import com.google.inject.Guice;
import com.google.inject.Injector;

import de.db12.common.guice.log.LoggingModule;
import de.db12.common.service.ProductService;

public class GuiceMain {
	public static void main(String[] args) {

		Injector injector = Guice.createInjector(new DozerBeanMappingModule(),
				new EbeanConfigurationModule(), new LoggingModule(),
				new SetupModule());
		ProductService service = injector.getInstance(ProductService.class);
		// List<Product> products = service.findAllProducts();
		// for (Product product : products) {
		// System.out.println(product);
		// }
	}
}
