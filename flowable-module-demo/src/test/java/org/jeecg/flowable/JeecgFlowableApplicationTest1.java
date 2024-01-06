package org.jeecg.flowable;

import org.jeecg.flowable.service.MyFirstService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = FlowableModelerDemoApplication.class)
public class JeecgFlowableApplicationTest1 {
    @Autowired
    private MyFirstService myService;
    @Test
    public void contextLoads() {
        myService.startProcess();
    }
}
