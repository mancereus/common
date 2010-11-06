package de.db12.common.main;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.google.inject.Guice;
import com.google.inject.Injector;

import de.db12.common.db.mybatis.BlogMapper;
import de.db12.common.entity.Blog;

public class GuiceMain {
	 public static void main(String[] args) {
		    /*
		     * Guice.createInjector() takes your Modules, and returns a new Injector
		     * instance. Most applications will call this method exactly once, in their
		     * main() method.
		     */
		    Injector injector = Guice.createInjector(new GuiceModule());
		    RealBillingService service = injector.getInstance(RealBillingService.class);
		    service.log.debug("test");
			 SqlSessionFactory sessionFactory = injector.getInstance(SqlSessionFactory.class);
		    
		    
		    SqlSession session = sessionFactory.openSession();
		    try {
		    BlogMapper mapper = session.getMapper(BlogMapper.class);
		    Blog blog = mapper.selectBlog(101);
		    } finally {
		    session.close();
		    }

		  }
}
