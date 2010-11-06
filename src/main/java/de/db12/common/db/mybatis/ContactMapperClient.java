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
package de.db12.common.db.mybatis;

import java.util.List;

import org.apache.ibatis.session.TransactionIsolationLevel;
import org.mybatis.guice.transactional.Transactional;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import de.db12.common.entity.Contact;
import de.db12.common.guice.mybatis.CustomException;

/**
 * 
 *
 * @version $Id: ContactMapperClient.java 2709 2010-10-14 20:44:06Z simone.tripodi $
 */
@Singleton
public class ContactMapperClient {

    private final ContactMapper contactMapper;

    @Inject
    public ContactMapperClient(ContactMapper contactMapper) {
        this.contactMapper = contactMapper;
    }

    @Transactional(
            isolationLevel = TransactionIsolationLevel.SERIALIZABLE,
            rethrowExceptionsAs = CustomException.class,
            exceptionMessage = "Impossible to insert {0} contact"
    )
    public void brokenInsert(final Contact contact) throws CustomException {
        this.contactMapper.brokenAdd(contact);
    }

    @Transactional(
            isolationLevel = TransactionIsolationLevel.SERIALIZABLE,
            rethrowExceptionsAs = CustomException.class,
            exceptionMessage = "Impossible to insert {0} contact"
    )
    public void insert(final Contact contact) throws CustomException {
        this.contactMapper.add(contact);
    }

    @Transactional(
            isolationLevel = TransactionIsolationLevel.SERIALIZABLE,
            rethrowExceptionsAs = CustomException.class,
            exceptionMessage = "Impossible to delete contact with ID {0}"
    )
    public void delete(final Integer id) throws CustomException {
        this.contactMapper.delete(id);
    }

    @Transactional(
            isolationLevel = TransactionIsolationLevel.SERIALIZABLE,
            rethrowExceptionsAs = CustomException.class,
            exceptionMessage = "An error occurred when selecting contact with ID {0}"
    )
    public Contact selectById(final Integer id) throws CustomException {
        return this.contactMapper.getById(id);
    }

    @Transactional(
            isolationLevel = TransactionIsolationLevel.SERIALIZABLE,
            rethrowExceptionsAs = CustomException.class,
            exceptionMessage = "An error occurred when selecting all stored contacts"
    )
    public List<Contact> getAll() throws CustomException {
        return this.contactMapper.selectAll();
    }

    @Transactional(
            isolationLevel = TransactionIsolationLevel.SERIALIZABLE,
            rethrowExceptionsAs = CustomException.class,
            exceptionMessage = "An error occurred when updating contact {0}"
    )
    public void update(final Contact contact) throws CustomException {
        this.contactMapper.update(contact);
    }

}
