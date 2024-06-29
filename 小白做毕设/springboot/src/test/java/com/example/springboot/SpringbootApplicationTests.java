package com.example.springboot;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.springboot.commen.Result;
import com.example.springboot.controller.WebController;
import com.example.springboot.entity.User;
import com.example.springboot.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SpringbootApplicationTests {

    @InjectMocks
    private WebController webController;

    @Mock
    private UserService userService;

    private User validUser;

    @BeforeEach
    void setUp() {
        validUser = new User();
        validUser.setUsername("user1234");
        validUser.setPassword("Password123");
        validUser.setRole("USER");
    }


    @DisplayName("注册新用户时的成功场景")
    @Test
    void register_withValidUser_shouldSucceed() {
        // Arrange
        when(userService.getOne(any(QueryWrapper.class))).thenReturn(null);
        when(userService.register(any(User.class))).thenReturn(validUser);

        // Act
        Result result = webController.register(validUser);

        // Assert
        assertNotNull(result);
        assertEquals(Result.CODE_SUCCESS, result.getCode());
        assertEquals("请求成功", result.getMsg());
        assertEquals(validUser, result.getData());

        // 验证userService的registerUser方法被调用了一次，并带有正确的参数
        verify(userService, times(1)).register(validUser);
    }

    @DisplayName("注册已存在用户时的失败场景")
    @Test
    public void register_withExistingUser_shouldFail() {
        // userService.getOne在用户存在时返回一个User对象
        User existingUser = new User();
        existingUser.setUsername("user1234");

        // 当调用userService.getOne时，返回存在的用户
        when(userService.getOne(any(QueryWrapper.class))).thenReturn(existingUser);

        // 创建一个新的User对象，尝试注册已存在的用户
        User newUser = new User();
        newUser.setUsername("user1234");
        newUser.setPassword("Password123");
        newUser.setRole("USER");

        // 调用register方法
        Result result = webController.register(newUser);

        // 验证结果
        assertNotNull(result);
        assertEquals(Result.CODE_SYS_ERROR, result.getCode()); // 假设注册失败返回400状态码
        assertEquals("用户已存在", result.getMsg()); // 验证返回的错误消息
        assertNull(result.getData());

        // 验证userService.register没有被调用
        verify(userService, never()).register(any(User.class));

        // 验证userService.getOne被调用了一次
        verify(userService, times(1)).getOne(any(QueryWrapper.class));
    }


    @DisplayName("用户名为空的场景")
    @Test
    void register_withEmptyUsername_shouldFail() {
        // Arrange
        User userWithEmptyUsername = new User();
        userWithEmptyUsername.setUsername("");
        userWithEmptyUsername.setPassword("Password123");
        //when(userService.register(any(User.class))).thenReturn(null);

        // Act
        Result result = webController.register(userWithEmptyUsername);

        // Assert
        assertNotNull(result);
        assertNotEquals(Result.CODE_SUCCESS, result.getCode());
        assertTrue(result.getMsg().contains("输入不合法"));
        assertNull(result.getData());
    }

    @DisplayName("密码不能为空的场景")
    @Test
    void register_withEmptyPassword_shouldFail() {
        // Arrange
        User userWithEmptyPassword = new User();
        userWithEmptyPassword.setUsername("user123");
        userWithEmptyPassword.setPassword("");


        // Act
        Result result = webController.register(userWithEmptyPassword);

        // Assert
        assertNotNull(result);
        assertNotEquals(Result.CODE_SUCCESS, result.getCode());
        assertTrue(result.getMsg().contains("输入不合法"));
        assertNull(result.getData());
    }

    @DisplayName("用户名长度不符合要求")
    @Test
    void register_withShortUsername_shouldFail() {
        // Arrange
        User userWithShortUsername = new User();
        userWithShortUsername.setUsername("a");
        userWithShortUsername.setPassword("Password123");
        userWithShortUsername.setRole("USER");


        // Act
        Result result = webController.register(userWithShortUsername);

        // Assert
        assertNotNull(result);
        assertNotEquals(Result.CODE_SUCCESS, result.getCode());
        assertTrue(result.getMsg().contains("用户名或密码过短"));
        assertNull(result.getData());
    }

    @DisplayName("密码长度不符合要求")
    @Test
    void register_withShortPassword_shouldFail() {
        // Arrange
        User userWithShortPassword = new User();
        userWithShortPassword.setUsername("user123");
        userWithShortPassword.setPassword("1");
        userWithShortPassword.setRole("USER");


        // Act
        Result result = webController.register(userWithShortPassword);

        // Assert
        assertNotNull(result);
        assertNotEquals(Result.CODE_SUCCESS, result.getCode());
        assertTrue(result.getMsg().contains("用户名或密码过短"));
        assertNull(result.getData());
    }


    @DisplayName("用户名长度不符合要求")
    @Test
    void register_withShortUsernameMore10_shouldFail() {
        // Arrange
        User userWithShortUsername = new User();
        userWithShortUsername.setUsername("user12345678901");
        userWithShortUsername.setPassword("Password123");
        userWithShortUsername.setRole("USER");


        // Act
        Result result = webController.register(userWithShortUsername);

        // Assert
        assertNotNull(result);
        assertNotEquals(Result.CODE_SUCCESS, result.getCode());
        assertTrue(result.getMsg().contains("用户名或密码过长"));
        assertNull(result.getData());
    }


    @DisplayName("密码长度不符合要求")
    @Test
    void register_withShortPasswordMore20_shouldFail() {
        // Arrange
        User userWithShortPassword = new User();
        userWithShortPassword.setUsername("user123");
        userWithShortPassword.setPassword("Password12345678901234567890");
        userWithShortPassword.setRole("USER");


        // Act
        Result result = webController.register(userWithShortPassword);

        // Assert
        assertNotNull(result);
        assertNotEquals(Result.CODE_SUCCESS, result.getCode());
        assertTrue(result.getMsg().contains("用户名或密码过长"));
        assertNull(result.getData());
    }



    @DisplayName("用户名边界值测试")
    @Test
    void register_withUsernameLength10_shouldSucceed() {
        // Arrange
        User userWithUsernameLength10 = new User();
        userWithUsernameLength10.setUsername("user123456"); // 10个字符
        userWithUsernameLength10.setPassword("Password123");
        userWithUsernameLength10.setRole("USER");
        when(userService.getOne(any(QueryWrapper.class))).thenReturn(null);
        when(userService.register(any(User.class))).thenReturn(userWithUsernameLength10);

        // Act
        Result result = webController.register(userWithUsernameLength10);

        // Assert
        assertNotNull(result);
        assertEquals(Result.CODE_SUCCESS, result.getCode());
        assertEquals("请求成功", result.getMsg());
        assertEquals(userWithUsernameLength10, result.getData());
    }

    @DisplayName("密码边界值测试")
    @Test
    void register_withPasswordLength20_shouldSucceed() {
        // Arrange
        User userWithPasswordLength20 = new User();
        userWithPasswordLength20.setUsername("user123");
        userWithPasswordLength20.setPassword("Password1234567890"); // 20个字符
        userWithPasswordLength20.setRole("USER");
        when(userService.getOne(any(QueryWrapper.class))).thenReturn(null);
        when(userService.register(any(User.class))).thenReturn(userWithPasswordLength20);

        // Act
        Result result = webController.register(userWithPasswordLength20);

        // Assert
        assertNotNull(result);
        assertEquals(Result.CODE_SUCCESS, result.getCode());
        assertEquals("请求成功", result.getMsg());
        assertEquals(userWithPasswordLength20, result.getData());
    }

    @DisplayName("角色为空")
    @Test
    void register_withEmptyRole_shouldFail() {
        // Arrange
        User userWithEmptyRole = new User();
        userWithEmptyRole.setUsername("user123");
        userWithEmptyRole.setPassword("Password123");
        userWithEmptyRole.setRole("");
        // Act
        Result result = webController.register(userWithEmptyRole);

        // Assert
        assertNotNull(result);
        assertNotEquals(Result.CODE_SUCCESS, result.getCode());
        assertTrue(result.getMsg().contains("输入不合法"));
        assertNull(result.getData());
    }






}
