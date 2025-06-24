package com.topicossoftware.marino.upb.edu.api.app.utils;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = BCryptImpl.class)
class BCryptImplTest {

    @Autowired
    private ApplicationContext context;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void passwordEncoderBeanIsLoaded() {
        assertNotNull(passwordEncoder, "PasswordEncoder bean should be loaded");
        assertTrue(passwordEncoder instanceof org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder,
                "PasswordEncoder should be an instance of BCryptPasswordEncoder");
    }

    @Test
    void passwordEncoderEncryptsPasswordCorrectly() {
        String rawPassword = "mySecret123";
        String encodedPassword = passwordEncoder.encode(rawPassword);

        assertNotNull(encodedPassword);
        assertNotEquals(rawPassword, encodedPassword);
        assertTrue(passwordEncoder.matches(rawPassword, encodedPassword),
                "Encoded password should match the raw password");
    }
}
