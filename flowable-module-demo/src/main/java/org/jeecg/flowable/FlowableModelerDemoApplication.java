package org.jeecg.flowable;

import org.flowable.ui.common.security.FlowableUiSecurityAutoConfiguration;
import org.jeecg.flowable.config.flowable.ApplicationConfiguration;
import org.jeecg.flowable.config.flowable.AppDispatcherServletConfiguration;
import org.jeecg.flowable.config.flowable.DatabaseAutoConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

//启用全局异常拦截器
@Import(value = {
        // 引入修改的配置
        ApplicationConfiguration.class,
        AppDispatcherServletConfiguration.class,
        // 引入 DatabaseConfiguration 表更新转换,
        DatabaseAutoConfiguration.class
})

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
// @SpringBootApplication
public class FlowableModelerDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(FlowableModelerDemoApplication.class, args);
    }
}

