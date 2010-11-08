package de.db12.common.main;

import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;

import com.google.inject.Inject;

import de.db12.common.guice.log.InjectLogger;

class RealBillingService {
	@InjectLogger
	Logger log;


	@Inject
	RealBillingService() {
	}

}
