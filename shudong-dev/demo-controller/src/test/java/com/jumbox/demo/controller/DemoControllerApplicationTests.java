package com.jumbox.demo.controller;

import com.jumbox.demo.mbg.mapper.UserMapper;
import com.jumbox.demo.mbg.pojo.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
//由于是Web项目，Junit需要模拟ServletContext，因此我们需要给我们的测试类加上@WebAppConfiguration。
@WebAppConfiguration
public class DemoControllerApplicationTests {

    @Resource
    private UserMapper userMapper;

    @Test
    public void contextLoads() {
    }

}
