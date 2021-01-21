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
package com.alibaba.csp.sentinel.dashboard.rule.zookeeper.gateway.flow;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.gateway.GatewayFlowRuleEntity;
import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.ParamFlowRuleEntity;
import com.alibaba.csp.sentinel.dashboard.rule.DynamicRulePublisher;
import com.alibaba.csp.sentinel.dashboard.rule.nacos.NacosConfigUtil;
import com.alibaba.csp.sentinel.dashboard.rule.zookeeper.ZookeeperConfigUtil;
import com.alibaba.csp.sentinel.dashboard.util.JSONUtils;
import com.alibaba.csp.sentinel.util.AssertUtil;
import com.alibaba.nacos.api.config.ConfigService;
import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author Eric Zhao
 * @description 写入网关限流规则
 * @since 1.4.0
 */
@ConditionalOnProperty(prefix = "data" ,name="source",havingValue = "zookeeper")
@Component("gateWayFlowRuleZookeeperPublisher")
public class GateWayFlowRuleZookeeperPublisher implements DynamicRulePublisher<List<GatewayFlowRuleEntity>> {

    @Autowired
    private CuratorFramework zkClient;

    @Override
    public void publish(String app, List<GatewayFlowRuleEntity> rules) throws Exception {
        AssertUtil.notEmpty(app, "app name cannot be empty");

        String path = ZookeeperConfigUtil.getPath(app,ZookeeperConfigUtil.GATEWAY_DATA_ID_POSTFIX);
        Stat stat = zkClient.checkExists().forPath(path);
        if (stat == null) {
            zkClient.create().creatingParentContainersIfNeeded().withMode(CreateMode.PERSISTENT).forPath(path, null);
        }
        byte[] data = CollectionUtils.isEmpty(rules) ? "[]".getBytes() : JSONUtils.toJSONString(rules).getBytes();
        zkClient.setData().forPath(path, data);
    }

//    @Override
//    public void publish(String app, List<GatewayFlowRuleEntity> rules) throws Exception {
////本次注释
////        AssertUtil.notEmpty(app, "app name cannot be empty");
////        if (rules == null) {
////            return;
////        }
////        configService.publishConfig(app + NacosConfigUtil.FLOW_DATA_ID_POSTFIX,
////            NacosConfigUtil.GROUP_ID, converter.convert(rules));
//
//        //本次新增
//        NacosConfigUtil.setRuleStringToNacos(
//                this.configService,
//                app,
//                NacosConfigUtil.GATEWAY_DATA_ID_POSTFIX,
//                rules
//        );
//
//    }
}
