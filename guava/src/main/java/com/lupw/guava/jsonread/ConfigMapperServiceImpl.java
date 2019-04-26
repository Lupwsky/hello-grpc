package com.lupw.guava.jsonread;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Objects;

/**
 * @author v_pwlu 2019/4/22
 */
@Slf4j
@Component
public class ConfigMapperServiceImpl {

    private Map<String, String> areaMapper;
    private Map<String, String> hospitalConfMapper;

    public ConfigMapperServiceImpl() {
        areaMapper = Maps.newHashMap();
        hospitalConfMapper = Maps.newHashMap();
    }

    public synchronized void init() {
        String traceId = "test";
        if (!areaMapper.isEmpty()) {
            areaMapper.clear();
        }

        if (!hospitalConfMapper.isEmpty()) {
            hospitalConfMapper.clear();
        }
        readMapperData(traceId, "area.txt", 0,areaMapper);
        readMapperData(traceId, "hospital_conf.txt", 1,hospitalConfMapper);
    }


    private void readMapperData(String traceId, String path, int type, Map<String, String> mapper) {
        if (type < 0 || type > 2) {
            throw new RuntimeException("readMapperData 不支持的类型");
        }

        FileInputStream fileInputStream;
        InputStreamReader inputStreamReader = null;
        StringBuilder stringBuilder = null;
        try {
            ClassPathResource classPathResource = new ClassPathResource(path);
            inputStreamReader = new InputStreamReader(classPathResource.getInputStream(), StandardCharsets.UTF_8);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String readValue;
            String readValueName = null, readValueCode = null;
            while ((readValue = bufferedReader.readLine()) != null) {
                if (type == 0) {
                    initAreaMapeerData(traceId, readValue, mapper);
                } else {
                    if (readValueName == null) {
                        if (readValue.contains("name")) {
                            readValueName = readValue;
                        }
                    } else {
                        if (readValue.contains("code")) {
                            readValueCode = readValue;
                            initHospitalConfMapeerData(traceId, readValueName, readValueCode, mapper);
                        }
                        readValueName = null;
                    }
                }
            }
        } catch (IOException e) {
            log.info("traceId = {}, error = {}", traceId, e);
        } finally {
            try {
                if (inputStreamReader != null) {
                    inputStreamReader.close();
                }
            } catch (IOException ex) {
                log.info("traceId = {}, error = {}", traceId, ex);
            }
        }
    }

    private void initAreaMapeerData(String traceId, String value, Map<String, String> mapper) {
        // 110000:'北京市'
        if (StringUtils.isEmpty(value) || !value.contains(":")) {
            log.info("[initAreaMapeerData] 不符合要求的数据, traceId = {}, value = {}", traceId, value);
        } else {
            String[] arr = value.split(":");
            if (arr.length > 1) {
                mapper.put(arr[0], arr[1].replace("'", ""));
            } else {
                log.info("[initAreaMapeerData] 不符合要求的数据, traceId = {}, value = {}", traceId, value);
            }
        }
    }

    private void initHospitalConfMapeerData(String traceId, String readValueName, String readValueCode, Map<String, String> mapper) {
        // readValueName = [      "name": "昌吉市仁康堂中医院",]
        // readValueCode = [      "code": "6592028",]
        // 获取 name 的值
        readValueName = readValueName.replaceAll("[ \",]", "");
        String[] arrName = readValueName.split(":");
        if (arrName.length > 1) {
            readValueName = arrName[1];
        } else {
            readValueName = "";
        }

        // 获取 code 的值
        readValueCode = readValueCode.replaceAll("[ \",]", "");
        String[] arrCode = readValueCode.split(":");
        if (arrCode.length > 1) {
            readValueCode = arrCode[1];
        } else {
            readValueCode = "";
        }

        if (Objects.equals("", readValueCode)) {
            log.error("[initHospitalConfMapeerData] 不符合要求的数据, traceId = {}, readValueCode = {}, readValueName = {}",
                    traceId, readValueCode, readValueName);
        } else {
            mapper.put(readValueCode, readValueName);
        }
    }

    public String getAreaName(String code) {
        if (StringUtils.isEmpty(code)) {
            return "";
        } else {
            return areaMapper.getOrDefault(code, code);
        }
    }

    public String getHospitalConfAreaName(String code) {
        if (StringUtils.isEmpty(code)) {
            return "";
        } else {
            return hospitalConfMapper.getOrDefault(code, code);
        }
    }
}
