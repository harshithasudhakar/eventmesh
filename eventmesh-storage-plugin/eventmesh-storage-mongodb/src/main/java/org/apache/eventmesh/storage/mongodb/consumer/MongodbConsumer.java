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

package org.apache.eventmesh.storage.mongodb.consumer;

import org.apache.eventmesh.api.AbstractContext;
import org.apache.eventmesh.api.EventListener;
import org.apache.eventmesh.api.consumer.Consumer;
import org.apache.eventmesh.common.config.Config;
import org.apache.eventmesh.storage.mongodb.config.ConfigKey;
import org.apache.eventmesh.storage.mongodb.config.ConfigurationHolder;

import java.util.List;
import java.util.Properties;

import io.cloudevents.CloudEvent;

@Config(field = "configurationHolder")
public class MongodbConsumer implements Consumer {

    private ConfigurationHolder configurationHolder;

    private Consumer consumer;

    @Override
    public boolean isStarted() {
        return consumer.isStarted();
    }

    @Override
    public boolean isClosed() {
        return consumer.isClosed();
    }

    @Override
    public void start() {
        consumer.start();
    }

    @Override
    public void shutdown() {
        consumer.shutdown();
    }

    @Override
    public void init(Properties keyValue) throws Exception {
        String connectorType = configurationHolder.getConnectorType();
        if (connectorType.equals(ConfigKey.STANDALONE)) {
            consumer = new MongodbStandaloneConsumer(configurationHolder);
        }
        if (connectorType.equals(ConfigKey.REPLICA_SET)) {
            consumer = new MongodbReplicaSetConsumer(configurationHolder);
        }
        consumer.init(keyValue);
    }

    @Override
    public void updateOffset(List<CloudEvent> cloudEvents, AbstractContext context) {
        consumer.updateOffset(cloudEvents, context);
    }

    @Override
    public void subscribe(String topic) throws Exception {
        consumer.subscribe(topic);
    }

    @Override
    public void unsubscribe(String topic) {
        consumer.unsubscribe(topic);
    }

    @Override
    public void registerEventListener(EventListener listener) {
        consumer.registerEventListener(listener);
    }
}
