package com.thread.excutor;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author v_pwlu 2018/10/11
 */
@Slf4j
public class TestMain {
    public static void main(String[] args) {

        List<DataNode> dataNodeList1 = new ArrayList<>();
        dataNodeList1.add(DataNode.builder().name("A").soc(1).build());
        dataNodeList1.add(DataNode.builder().name("B").soc(1).build());
        dataNodeList1.add(DataNode.builder().name("C").soc(1).build());
        dataNodeList1.add(DataNode.builder().name("D").soc(1).build());

        List<DataNode> dataNodeList2 = new ArrayList<>();
        dataNodeList2.add(DataNode.builder().name("E").soc(1).build());
        dataNodeList2.add(DataNode.builder().name("F").soc(1).build());
        dataNodeList2.add(DataNode.builder().name("G").soc(1).build());
        dataNodeList2.add(DataNode.builder().name("H").soc(1).build());

        List<DataNode> dataNodeList3 = new ArrayList<>();
        dataNodeList3.add(DataNode.builder().name("I").soc(1).build());
        dataNodeList3.add(DataNode.builder().name("J").soc(1).build());
        dataNodeList3.add(DataNode.builder().name("K").soc(1).build());
        dataNodeList3.add(DataNode.builder().name("L").soc(1).build());

        List<List<DataNode>> dataList = new ArrayList<>();
        dataList.add(dataNodeList1);
        dataList.add(dataNodeList2);
        dataList.add(dataNodeList3);

        List<DataNode> newDataList = dataList.stream().flatMap(Collection::stream).collect(Collectors.toList());
        log.info(newDataList.toString());
    }
}
