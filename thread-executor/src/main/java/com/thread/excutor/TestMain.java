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
        dataNodeList.add(DataNode.builder().name("F").soc(1).build());
        dataNodeList.add(DataNode.builder().name("G").soc(1).build());
        dataNodeList.add(DataNode.builder().name("H").soc(1).build());
        dataNodeList.add(DataNode.builder().name("I").soc(1).build());
        dataNodeList.add(DataNode.builder().name("J").soc(1).build());
        dataNodeList.add(DataNode.builder().name("K").soc(1).build());
        dataNodeList.add(DataNode.builder().name("L").soc(1).build());
        dataNodeList.add(DataNode.builder().name("M").soc(1).build());
        dataNodeList.add(DataNode.builder().name("N").soc(1).build());
        dataNodeList.add(DataNode.builder().name("O").soc(1).build());
        dataNodeList.add(DataNode.builder().name("P").soc(1).build());
        dataNodeList.add(DataNode.builder().name("Q").soc(1).build());
        dataNodeList.add(DataNode.builder().name("R").soc(1).build());
        dataNodeList.add(DataNode.builder().name("S").soc(1).build());
        dataNodeList.add(DataNode.builder().name("T").soc(1).build());
        dataNodeList.add(DataNode.builder().name("U").soc(1).build());
        dataNodeList.add(DataNode.builder().name("V").soc(1).build());
        dataNodeList.add(DataNode.builder().name("W").soc(1).build());
        dataNodeList.add(DataNode.builder().name("X").soc(1).build());
        dataNodeList.add(DataNode.builder().name("Y").soc(1).build());
        dataNodeList.add(DataNode.builder().name("Z").soc(1).build());

        DataNode initDataNode = DataNode.builder().name("I").soc(5).build();
        DataNode finalDataNode = dataNodeList.parallelStream().reduce(initDataNode, (result, item) -> {
            log.info("{} = {}", "result", JSON.toJSONString(result));
            log.info("{} = {}", "item  ", JSON.toJSONString(item));
            int total = result.getSoc() + item.getSoc();
            return DataNode.builder().name("N").soc(total).build();
        }, (result, item) -> {
            int total = result.getSoc() + item.getSoc();
            return DataNode.builder().name("F").soc(total).build();
        });
        log.info("finalDataNode" + JSON.toJSONString(finalDataNode));
    }
}
