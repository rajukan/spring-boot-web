package com.org.vg.service;


import java.util.List;

import com.org.vg.domain.User;

public interface UserService {

    User save(User user);

    List<User> getList();

}
