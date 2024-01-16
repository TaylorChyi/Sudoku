package com.taylorchyi.sudoku.model;

import org.junit.jupiter.api.Test;

import com.taylorchyi.sudoku.model.User;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void testUserGettersAndSetters() {
        User user = new User();
        user.setId(1L);
        user.setUsername("testUser");
        user.setPassword("testPass");

        assertEquals(1L, user.getId(), "User ID should be correctly set and retrieved.");
        assertEquals("testUser", user.getUsername(), "Username should be correctly set and retrieved.");
        assertEquals("testPass", user.getPassword(), "Password should be correctly set and retrieved.");
    }

    // 根据需要添加更多测试案例，例如检查加密密码的逻辑（如果实现）
}
