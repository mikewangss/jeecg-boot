package org.jeecg.modules.demo;

import org.apache.commons.io.IOUtils;
import org.jeecg.modules.demo.settlement.vo.ApplyInfoPage;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.IOException;
import java.io.InputStream;

@SpringBootTest
@AutoConfigureMockMvc
public class ApplyInfoControllerTest {
    @Autowired
    MockMvc mvc;
    private String readJson(String jsonSrc) {
        String json = "";
        try {
            InputStream stream = getClass().getClassLoader().getResourceAsStream(jsonSrc.replace("classpath:", ""));
            json = IOUtils.toString(stream,"UTF-8");
        } catch (IOException e) {
            System.out.println(e);
        }
        return json;
    }
    @Test
    void apply_new() throws Exception {

        String apply_info_json = readJson("classpath:org/jeecg/modules/demo/mock/json/apply_info.json");
        mvc.perform(MockMvcRequestBuilders.post("http://127.0.0.1:8080/settlement/applyInfo/add")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(apply_info_json)
                .accept(MediaType.APPLICATION_JSON_UTF8)
        );

    }
}
