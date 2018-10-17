package com.thread.excutor;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * @author v_pwlu 2018/10/11
 */
@Slf4j
public class TestMain {
    public static void main(String[] args) {
//        List<String> dataList = Arrays.asList("A", "B", "C", "D", "E", "F", "G");
//        String value1 = List2StringUtils.list2String(dataList, ";");
//        log.info(value1);
//
//        String value2 = List2StringUtils.list2String(dataList, ";", "[", "]");
//        log.info(value2);
//
//        List<String> emptyList = new ArrayList<>();
//        String value3 = List2StringUtils.list2String(emptyList, ";");
//        log.info(value3);

        List<DataNode> dataNodeList = new ArrayList<>();
        dataNodeList.add(DataNode.builder().name("A").soc(1).build());
        dataNodeList.add(DataNode.builder().name("B").soc(1).build());
        dataNodeList.add(DataNode.builder().name("C").soc(1).build());
        dataNodeList.add(DataNode.builder().name("D").soc(1).build());
        dataNodeList.add(DataNode.builder().name("E").soc(1).build());

        Optional<DataNode> optionalDataNode = dataNodeList.stream().reduce((dataNode, dataNode2) -> {
            log.info("{} = {}", "dataNode", JSON.toJSONString(dataNode));
            log.info("{} = {}", "dataNode2", JSON.toJSONString(dataNode2));
            int total = dataNode.getSoc() + dataNode2.getSoc();
            log.info("total = {}", total);
            return DataNode.builder().name("N").soc(total).build();
        });
        DataNode dataNode = optionalDataNode.orElse(DataNode.builder().build());
        log.info("finalDataNode" + JSON.toJSONString(dataNode));
    }
}
