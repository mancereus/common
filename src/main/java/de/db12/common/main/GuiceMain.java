package de.db12.common.main;

import com.google.inject.Guice;
import com.google.inject.Injector;

import de.db12.common.db.entity.Contact;
import de.db12.common.db.mybatis.ConfigureMybatisModule;
import de.db12.common.db.mybatis.CustomType;
import de.db12.common.guice.log.LoggingModule;
import de.db12.common.service.RealContactService;

public class GuiceMain {
	public static void main(String[] args) {
		/*
		 * Guice.createInjector() takes your Modules, and returns a new Injector
		 * instance. Most applications will call this method exactly once, in
		 * their main() method.
		 */
		Injector injector = Guice.createInjector(new DBConnectionModule(),
				new ConfigureMybatisModule(), new LoggingModule());
		RealContactService service = injector
				.getInstance(RealContactService.class);

		Contact contact = new Contact();
		contact.setFirstName("Matthias");
		contact.setLastName("Schmitt");
		contact.setCreated(new CustomType(System.currentTimeMillis()));
		contact.setAddress(null);
		service.insert(contact);
		// SqlSessionFactory sessionFactory = injector
		// .getInstance(SqlSessionFactory.class);
		//
		// SqlSession session = sessionFactory.openSession();
		// try {
		// BlogMapper mapper = session.getMapper(BlogMapper.class);
		// Blog blog = mapper.selectBlog(101);
		// } finally {
		// session.close();
		// }

	}
}
