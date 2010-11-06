/*
 *    Copyright 2010 The myBatis Team
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package de.db12.common.guice.mybatis;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.inject.Inject;
import com.google.inject.name.Named;

import de.db12.common.db.mybatis.AddressConverter;
import de.db12.common.db.mybatis.ContactMapperClient;
import de.db12.common.entity.Address;
import de.db12.common.entity.Contact;

/**
 *
 *
 * @version $Id: MyBatisModuleTestCase.java 2746 2010-10-18 19:22:10Z simone.tripodi $
 */
@RunWith(GuiceTestRunner.class)
public final class MyBatisModuleTestCase extends AbstractMyBatisModuleTestCase {

    @Inject
    @Named("contactWithAddress")
    private Contact contactWithAdress;

    @Inject
    private ContactMapperClient contactMapperClient;

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
        this.contactMapperClient.insert(this.contactWithAdress);
    }

    @Test
    public void selectContactWithAddress() throws Exception {
        Contact contact = this.contactMapperClient.selectById(this.contactWithAdress.getId());
        assert contact != null : "impossible to retrieve Contact with id '"
                                + this.contactWithAdress.getId()
                                + "'";
        assert this.contactWithAdress.equals(contact) : "Expected "
                                                + this.contactWithAdress
                                                + " but found "
                                                + contact;
    }

}
