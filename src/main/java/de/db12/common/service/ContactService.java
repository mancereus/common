package de.db12.common.service;

import java.util.List;

import org.apache.ibatis.session.TransactionIsolationLevel;
import org.mybatis.guice.transactional.Transactional;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import de.db12.common.db.entity.Contact;
import de.db12.common.db.mapper.ContactMapper;

@Singleton
public class ContactService {

	private final ContactMapper contactMapper;

	@Inject
	public ContactService(ContactMapper contactMapper) {
		this.contactMapper = contactMapper;
	}

	@Transactional(isolationLevel = TransactionIsolationLevel.SERIALIZABLE, rethrowExceptionsAs = CustomException.class, exceptionMessage = "Impossible to insert {0} contact")
	public void insert(final Contact contact) throws CustomException {
		this.contactMapper.add(contact);
	}

	@Transactional(isolationLevel = TransactionIsolationLevel.SERIALIZABLE, rethrowExceptionsAs = CustomException.class, exceptionMessage = "Impossible to delete contact with ID {0}")
	public void delete(final Integer id) throws CustomException {
		this.contactMapper.delete(id);
	}

	@Transactional(isolationLevel = TransactionIsolationLevel.SERIALIZABLE, rethrowExceptionsAs = CustomException.class, exceptionMessage = "An error occurred when selecting contact with ID {0}")
	public Contact selectById(final Integer id) throws CustomException {
		return this.contactMapper.getById(id);
	}

	@Transactional(isolationLevel = TransactionIsolationLevel.SERIALIZABLE, rethrowExceptionsAs = CustomException.class, exceptionMessage = "An error occurred when selecting all stored contacts")
	public List<Contact> getAll() throws CustomException {
		return this.contactMapper.selectAll();
	}

	@Transactional(isolationLevel = TransactionIsolationLevel.SERIALIZABLE, rethrowExceptionsAs = CustomException.class, exceptionMessage = "An error occurred when selecting all stored contacts")
	public List<Contact> getAllWithProfile() throws CustomException {
		return this.contactMapper.selectAllWithProfile();
	}

	@Transactional(isolationLevel = TransactionIsolationLevel.READ_COMMITTED, rethrowExceptionsAs = CustomException.class, exceptionMessage = "An error occurred when selecting all stored contacts ordered by {0}")
	public List<Contact> getAllOrdered() throws CustomException {
		return this.contactMapper.selectAllOrdered("LAST_NAME");
	}

	@Transactional(isolationLevel = TransactionIsolationLevel.SERIALIZABLE, rethrowExceptionsAs = CustomException.class, exceptionMessage = "An error occurred when updating contact {0}")
	public void update(final Contact contact) throws CustomException {
		this.contactMapper.update(contact);
	}

}
