package org.jeecg.modules.demo.settlement.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.File;
import java.io.InputStream;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.util.ResourceUtils;

@Slf4j
public class GetJsonData {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static <T> List<T> readJsonFile(String filePath, Class<T> valueType) {
        List<T> dataList = null;
        File jsonFile = null;
        try {
            jsonFile = ResourceUtils.getFile(filePath);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            dataList = objectMapper.readValue(jsonFile, objectMapper.getTypeFactory().constructCollectionType(List.class, valueType));
        } catch (IOException e) {
            e.printStackTrace();
            // 可以抛出自定义异常或进行其他错误处理
            log.error(e.getMessage(), e);
        }

        return dataList;
    }

}



