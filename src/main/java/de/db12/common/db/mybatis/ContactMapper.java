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

import de.db12.common.entity.Contact;

/**
 * 
 *
 * @version $Id: ContactMapper.java 2456 2010-09-16 15:28:04Z simone.tripodi $
 */
public interface ContactMapper {

    void brokenAdd(Contact contact);

    void add(Contact contact);

    void update(Contact contact);

    void delete(Integer id);

    Contact getById(Integer id);

    List<Contact> selectAll();

}
