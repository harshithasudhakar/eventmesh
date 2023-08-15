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

package org.apache.eventmesh.connector.pulsar.server;


import org.apache.eventmesh.connector.pulsar.config.PulsarServerConfig;
import org.apache.eventmesh.connector.pulsar.sink.connector.PulsarSinkConnector;
import org.apache.eventmesh.openconnect.Application;
import org.apache.eventmesh.openconnect.util.ConfigUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PulsarConnectServer {


    public static void main(String[] args) throws Exception {

        PulsarServerConfig serverConfig = ConfigUtil.parse(PulsarServerConfig.class, "server-config.yml");

        if (serverConfig.isSourceEnable()) {
            Application rocketmqSourceApp = new Application();
            rocketmqSourceApp.run(PulsarSinkConnector.class);
        }

        if (serverConfig.isSinkEnable()) {
            Application rocketmqSinkApp = new Application();
            rocketmqSinkApp.run(PulsarSinkConnector.class);
        }
    }
}
