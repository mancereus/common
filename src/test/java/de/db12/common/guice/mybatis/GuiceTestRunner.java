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

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.junit.runners.model.InitializationError;
import org.mybatis.guice.MyBatisModule;
import org.mybatis.guice.datasource.builtin.PooledDataSourceProvider;
import org.mybatis.guice.datasource.helper.JdbcHelper;

import com.google.inject.Module;

import de.db12.common.db.mybatis.AddressTypeHandler;
import de.db12.common.db.mybatis.ContactMapper;
import de.db12.common.entity.Address;

/**
 * 
 *
 * @version $Id: GuiceTestRunner.java 3031 2010-11-03 14:04:14Z simone.tripodi $
 */
public final class GuiceTestRunner extends AbstractGuiceTestRunner {

    public GuiceTestRunner(Class<?> klass) throws InitializationError {
        super(klass);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected List<Module> createMyBatisModule() {
        List<Module> modules = new ArrayList<Module>(3);

        modules.add(JdbcHelper.HSQLDB_Embedded);
        modules.add(new MyBatisModule.Builder()
                        .setDataSourceProviderType(PooledDataSourceProvider.class)
                        .addMapperClasses(ContactMapper.class)
                        .addTypeHandler(CustomType.class, CustomLongTypeHandler.class)
                        .addTypeHandler(Address.class, AddressTypeHandler.class)
                        .addInterceptorsClasses(CountUpdateInterceptor.class)
                        .create());

        return modules;
    }

    @Override
    protected Properties createTestProperties() {
        final Properties myBatisProperties = new Properties();
        myBatisProperties.setProperty("mybatis.environment.id", "test");
        myBatisProperties.setProperty("JDBC.schema", "mybatis-guice_TEST");
        myBatisProperties.setProperty("derby.create", "true");
        myBatisProperties.setProperty("JDBC.username", "sa");
        myBatisProperties.setProperty("JDBC.password", "");
        myBatisProperties.setProperty("JDBC.autoCommit", "false");
        return myBatisProperties;
    }

}
