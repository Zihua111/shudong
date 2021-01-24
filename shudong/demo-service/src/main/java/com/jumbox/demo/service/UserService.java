package com.jumbox.demo.service;

import com.jumbox.demo.mbg.pojo.User;

import java.util.List;

public interface UserService {

    public List<User> getAdminList();

    public User login(String id,String username);

    public User adminRegister(String username);

}
