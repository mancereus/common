package de.db12.common.service;

import org.apache.ibatis.session.TransactionIsolationLevel;
import org.mybatis.guice.transactional.Transactional;
import org.slf4j.Logger;

import com.google.inject.Inject;

import de.db12.common.db.entity.Contact;
import de.db12.common.db.mapper.ContactMapper;
import de.db12.common.guice.log.InjectLogger;

public class RealContactService {
	@InjectLogger
	Logger log;
	private final ContactMapper contactMapper;

	@Inject
	public RealContactService(ContactMapper contactMapper) {
		this.contactMapper = contactMapper;
	}

	@Transactional(isolationLevel = TransactionIsolationLevel.READ_COMMITTED, rethrowExceptionsAs = CustomException.class, exceptionMessage = "Impossible to insert {0} contact")
	public void insert(final Contact contact) {
		this.contactMapper.add(contact);
	}

}
