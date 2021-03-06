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
package com.alibaba.csp.sentinel.dashboard.rule.zookeeper.param;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.FlowRuleEntity;
import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.ParamFlowRuleEntity;
import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.SystemRuleEntity;
import com.alibaba.csp.sentinel.dashboard.rule.DynamicRuleProvider;
import com.alibaba.csp.sentinel.dashboard.rule.zookeeper.ZookeeperConfigUtil;
import com.alibaba.csp.sentinel.dashboard.util.JSONUtils;
import com.alibaba.csp.sentinel.datasource.Converter;
import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.data.Stat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
/**
 * @Description 读取热点限流规则
 * @Author shiyanling
 * @Date 2021/1/21 16:00
 **/
@ConditionalOnProperty(prefix = "data" ,name="source",havingValue = "zookeeper")
@Component("paramFlowRuleZookeeperProvider")
public class ParamFlowRuleZookeeperProvider implements DynamicRuleProvider<List<ParamFlowRuleEntity>> {

    @Autowired
    private CuratorFramework zkClient;
//    @Autowired
//    private Converter<String, List<ParamFlowRuleEntity>> converter;

    @Override
    public List<ParamFlowRuleEntity> getRules(String appName) throws Exception {
        String zkPath = ZookeeperConfigUtil.getPath(appName,ZookeeperConfigUtil.PARAM_FLOW_DATA_ID_POSTFIX);
        Stat stat = zkClient.checkExists().forPath(zkPath);
        if(stat == null){
            return new ArrayList<>(0);
        }
        byte[] bytes = zkClient.getData().forPath(zkPath);
        if (null == bytes || bytes.length == 0) {
            return new ArrayList<>();
        }
        String s = new String(bytes);

        return JSONUtils.parseObject(ParamFlowRuleEntity.class,s);
    }
}