package org.jeecg.flowable.config.flowable;
import org.flowable.ui.common.security.ApiHttpSecurityCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

@Configuration
public class FlowableSecurityConfig {

    @Bean
    public ApiHttpSecurityCustomizer apiHttpSecurityCustomizer()  {
        return new ApiHttpSecurityCustomizer() {
            @Override
            public void customize(HttpSecurity httpSecurity) throws Exception {
                // 在这里可以添加自定义的 Spring Security 配置
                // 例如，设置特定的路径不需要身份验证等
                httpSecurity
                        //必须要将csrf设置为disable，不然后面发送POST请求时会报403错误
                        .csrf().disable()
                        //为了简单起见，简单粗暴方式直接放行modeler下面所有请求
                        .authorizeRequests().antMatchers("/modeler/**").permitAll();
            }


        };
    }
}

