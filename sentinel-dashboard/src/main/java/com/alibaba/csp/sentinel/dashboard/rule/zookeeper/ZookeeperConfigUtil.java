/*
 * Copyright 1999-2018 Alibaba Group Holding Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.alibaba.csp.sentinel.dashboard.rule.zookeeper;


import org.apache.commons.lang.StringUtils;

public class ZookeeperConfigUtil {
    public static final String RULE_ROOT_PATH = "/sentinel_rule_config";
    public static final String GROUP_ID = "SENTINEL_GROUP";
    public static final String FLOW_DATA_ID_POSTFIX = "flow-rules";
    public static final String PARAM_FLOW_DATA_ID_POSTFIX = "param-rules";
    public static final String CLUSTER_MAP_DATA_ID_POSTFIX = "cluster-map";
    public static final String DEGRADE_DATA_ID_POSTFIX = "degrade-rules";
    public static final String SYSTEM_DATA_ID_POSTFIX = "system-rules";
    public static final String AUTHORITY_DATA_ID_POSTFIX = "authority-rules";

    public static final int RETRY_TIMES = 3;
    public static final int SLEEP_TIME = 1000;





    public static String getPath(String appName,String postfix) {
        StringBuilder stringBuilder = new StringBuilder(RULE_ROOT_PATH);
        if (StringUtils.isBlank(appName)) {
            return stringBuilder.toString();
        }
        if (appName.startsWith("/")) {
            stringBuilder.append(GROUP_ID).append("/").append(appName).append("/").append(postfix);
        } else {
            stringBuilder.append("/")
                    .append(GROUP_ID).append("/").append(appName).append("/").append(postfix);
        }
        return stringBuilder.toString();
    }
}