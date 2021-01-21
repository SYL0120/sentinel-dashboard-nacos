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
package com.alibaba.csp.sentinel.dashboard.rule.nacos.gateway.flow;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.gateway.GatewayFlowRuleEntity;
import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.FlowRuleEntity;
import com.alibaba.csp.sentinel.dashboard.rule.DynamicRuleProvider;
import com.alibaba.csp.sentinel.dashboard.rule.nacos.NacosConfigUtil;
import com.alibaba.nacos.api.config.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @description 流控规则
 * @author Eric Zhao
 * @since 1.4.0
 */
@ConditionalOnProperty(prefix = "data" ,name="source",havingValue = "nacos")
@Component("gateWayFlowRuleNacosProvider")
public class GateWayFlowRuleNacosProvider implements DynamicRuleProvider<List<GatewayFlowRuleEntity>> {

    @Autowired
    private ConfigService configService;
//本次注释
//    @Autowired
//    private Converter<String, List<FlowRuleEntity>> converter;

    @Override
    public List<GatewayFlowRuleEntity> getRules(String appName) throws Exception {
//本次注释
//        String rules = configService.getConfig(appName + NacosConfigUtil.FLOW_DATA_ID_POSTFIX,
//            NacosConfigUtil.GROUP_ID, 3000);
//        if (StringUtil.isEmpty(rules)) {
//            return new ArrayList<>();
//        }
//        return converter.convert(rules);
        //本次新增
        return NacosConfigUtil.getRuleEntitiesFromNacos(
                this.configService,
                appName,
                NacosConfigUtil.GATEWAY_DATA_ID_POSTFIX,
                GatewayFlowRuleEntity.class
        );

    }
}
