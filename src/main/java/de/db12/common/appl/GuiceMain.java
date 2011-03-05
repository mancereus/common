package de.db12.common.appl;

import java.util.List;
import com.google.inject.Guice;
import com.google.inject.Injector;
import de.db12.common.db.data.Product;
import de.db12.common.guice.EbeanConfigurationModule;
import de.db12.common.guice.dozer.DozerBeanMappingModule;
import de.db12.common.guice.log.LoggingModule;
import de.db12.common.service.ProductService;

public class GuiceMain {
	public static void main(String[] args) {

		Injector injector = Guice.createInjector(new DozerBeanMappingModule(), new EbeanConfigurationModule(), new LoggingModule(), new SetupModule());
		ProductService service = injector.getInstance(ProductService.class);
		List<Product> products = service.findAllProducts();
		System.err.print("test");
		for (Product product : products) {
			System.out.println(product);
		}
	}
}
