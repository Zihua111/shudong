package com.jumbox.demo.service;

import com.jumbox.demo.mbg.mapper.UserMapper;
import com.jumbox.demo.mbg.pojo.User;
import org.n3r.idworker.Sid;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<User> getAdminList() {
        Example example = new Example(User.class);
        example.createCriteria().andEqualTo("role", 1);
        return userMapper.selectByExample(example);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public User login(String id, String username) {
        //查询是否有相同id的用户
        User userCheck = userMapper.selectByPrimaryKey(id);
        if (userCheck == null) {
            //创建用户实例
            User user = new User();
            user.setId(id);
            user.setUsername(username);
            user.setRole(0);
            user.setCreateTime(new Date());
            userMapper.insert(user);
            return user;
        } else {
            return userCheck;
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public User adminRegister(String username) {
            User user = new User();
            user.setId(Sid.next());
            user.setUsername(username);
            user.setRole(1);
            user.setCreateTime(new Date());
            userMapper.insert(user);
            return user;
    }

}
