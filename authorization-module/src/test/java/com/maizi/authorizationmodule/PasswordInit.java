package com.maizi.authorizationmodule;

import com.maizi.service.service.UsersService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
public class PasswordInit {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    UsersService usersService;


    @Test
    public void initUser() {
    }


    @Test
    public void reEncodePasswords() {
        usersService.reEncodePasswords();
    }


}
