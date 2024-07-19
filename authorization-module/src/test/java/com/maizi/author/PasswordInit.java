package com.maizi.author;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
public class PasswordInit {

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Test
    public void initUser() {
    }


    @Test
    public void reEncodePasswords() {

        // usersService.reEncodePasswords();


    }
}
