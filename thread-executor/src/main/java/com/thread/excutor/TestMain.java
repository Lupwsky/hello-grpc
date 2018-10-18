package com.thread.excutor;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.function.Function;

/**
 * @author v_pwlu 2018/10/11
 */
@Slf4j
public class TestMain {
    public static void main(String[] args) {

        List<DataNode> dataNodeList = new ArrayList<>();
        dataNodeList.add(DataNode.builder().name("A").soc(1).build());
        dataNodeList.add(DataNode.builder().name("B").soc(2).build());
        dataNodeList.add(DataNode.builder().name("C").soc(3).build());
        dataNodeList.add(DataNode.builder().name("D").soc(4).build());

        List<String> list = map(dataNodeList, DataNode::getName);
        log.info(list.toString());
    }


    private static List<String> map(List<DataNode> dataNodeList, Function<DataNode, String> function) {
        List<String> returnDataList = new ArrayList<>();
        for (DataNode dataNode : dataNodeList) {
            returnDataList.add(function.apply(dataNode));
        }
        return returnDataList;
    }
}
