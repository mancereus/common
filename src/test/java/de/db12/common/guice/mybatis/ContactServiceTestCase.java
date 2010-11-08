package de.db12.common.guice.mybatis;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.inject.Inject;
import com.google.inject.name.Named;

import de.db12.common.db.entity.Address;
import de.db12.common.db.entity.Contact;
import de.db12.common.db.mybatis.AddressConverter;
import de.db12.common.service.ContactService;

@RunWith(GuiceTestRunner.class)
public final class ContactServiceTestCase extends AbstractContactMapperTestCase {

	@Inject
	@Named("contactWithAddress")
	private Contact contactWithAdress;

	@Inject
	private ContactService contactService;

	@Inject
	private AddressConverter addressConverter;

	@Test
	public void testAddressConverter() throws Exception {
		Address address = new Address();
		address.setNumber(1234);
		address.setStreet("Elm street");
		assert "1234 Elm street".equals(addressConverter.convert(address));
		assert address.equals(addressConverter.convert("1234 Elm street"));
	}

	@Test
	public void insertContactWithAddress() throws Exception {
		Address address = new Address();
		address.setNumber(1234);
		address.setStreet("Elm street");
		this.contactWithAdress.setAddress(address);
		this.contactService.insert(this.contactWithAdress);
	}

	@Test
	public void selectContactWithAddress() throws Exception {
		Contact contact = this.contactService.selectById(this.contactWithAdress
				.getId());
		assert contact != null : "impossible to retrieve Contact with id '"
				+ this.contactWithAdress.getId() + "'";
		assert this.contactWithAdress.equals(contact) : "Expected "
				+ this.contactWithAdress + " but found " + contact;
	}

}
