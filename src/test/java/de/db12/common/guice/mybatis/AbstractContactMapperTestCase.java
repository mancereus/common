package de.db12.common.guice.mybatis;

import static junit.framework.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;

import com.google.inject.Inject;

import de.db12.common.db.entity.Contact;
import de.db12.common.db.mybatis.Counter;
import de.db12.common.service.ContactService;

public abstract class AbstractContactMapperTestCase {

	@Inject
	private Contact contact;

	@Inject
	private ContactService contactService;

	@Inject
	private Counter counter;

	public void setContact(Contact contact) {
		this.contact = contact;
	}

	public void setContactService(ContactService contactMapperClient) {
		this.contactService = contactMapperClient;
	}

	@Test
	public void verifyNotNullService() {
		assertNotNull(this.contactService);
	}

	@Test
	public void insertContact() throws Exception {
		this.contactService.insert(this.contact);
	}

	@Test
	public void selectAllContacts() throws Exception {
		List<Contact> contacts = this.contactService.getAll();
		assert contacts.size() > 0 : "Expected not empty contact table";
	}

	@Test
	public void reSelectAllContacts() throws Exception {
		List<Contact> contacts = this.contactService.getAll();
		assert contacts.size() > 0 : "Expected not empty contact table";
	}

	@Test
	public void selectAllContactsWithProfile() throws Exception {
		List<Contact> contacts = this.contactService.getAllWithProfile();
		assert contacts.size() > 0 : "Expected not empty contact table";
	}

	@Test
	public void selectAllContactsOrdered() throws Exception {
		List<Contact> contacts = this.contactService.getAllOrdered();
		assert contacts.size() > 0 : "Expected not empty contact table";
	}

	@Test
	public void selectContact() throws Exception {
		Contact contact = this.contactService.selectById(this.contact.getId());
		assert contact != null : "impossible to retrieve Contact with id '"
				+ this.contact.getId() + "'";
		assert this.contact.equals(contact) : "Expected " + this.contact
				+ " but found " + contact;
	}

	@Test
	public void testCountInterceptor() throws Exception {
		counter.reset();
		assert 0 == counter.getCount() : "Expected 0 update in counter, but was "
				+ counter.getCount();
		this.contactService.update(contact);
		assert 1 == counter.getCount() : "Expected 1 update in Executor, but was "
				+ counter.getCount();
	}

}
