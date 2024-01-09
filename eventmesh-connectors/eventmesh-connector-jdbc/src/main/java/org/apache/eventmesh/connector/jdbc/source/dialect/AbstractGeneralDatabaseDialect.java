/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.eventmesh.connector.jdbc.source.dialect;

import org.apache.eventmesh.connector.jdbc.DatabaseDialect;
import org.apache.eventmesh.connector.jdbc.connection.JdbcConnection;
import org.apache.eventmesh.connector.jdbc.exception.JdbcConnectionException;
import org.apache.eventmesh.connector.jdbc.source.config.SourceConnectorConfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class AbstractGeneralDatabaseDialect<JC extends JdbcConnection> implements DatabaseDialect<JC> {

    private static final int DEFAULT_BATCH_MAX_ROWS = 20;

    private SourceConnectorConfig config;

    private int batchMaxRows = DEFAULT_BATCH_MAX_ROWS;

    public AbstractGeneralDatabaseDialect(SourceConnectorConfig config) {
        this.config = config;
    }

    @Override
    public boolean isValid(Connection connection, int timeout) throws JdbcConnectionException, SQLException {
        return connection == null ? false : connection.isValid(timeout);
    }

    @Override
    public PreparedStatement createPreparedStatement(Connection connection, String sql) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        if (batchMaxRows > 0) {
            preparedStatement.setFetchSize(batchMaxRows);
        }
        return preparedStatement;
    }

}
