package com.siasun.dao.base;

import com.siasun.entities.User;

import java.util.List;

/**
 * Created by hliu on 2016/12/3.
 */
public interface UserDao {
    List<User> findAllUsers();
}
