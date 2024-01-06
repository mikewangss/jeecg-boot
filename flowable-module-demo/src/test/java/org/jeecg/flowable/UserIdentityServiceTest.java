package org.jeecg.flowable;

import org.flowable.engine.IdentityService;
import org.flowable.idm.engine.impl.persistence.entity.UserEntityImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = FlowableModelerDemoApplication.class)
public class UserIdentityServiceTest {
    @Autowired
    IdentityService identityService;
    @Test
    public void contextLoads() {
        UserEntityImpl user = new UserEntityImpl();
        user.setId("javaboy");
        user.setDisplayName("江南一点雨");
        user.setPassword("123");
        user.setFirstName("java");
        user.setLastName("boy");
        user.setEmail("javaboy@qq.com");
        user.setRevision(0);
        identityService.saveUser(user);
    }

}
