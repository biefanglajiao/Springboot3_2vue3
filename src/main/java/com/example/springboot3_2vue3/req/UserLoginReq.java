package com.example.springboot3_2vue3.req;

import jakarta.validation.constraints.NotEmpty;

/**
 * @Author: 常兆海
 * @Description:
 * @DateTime: 2023/6/10 19:00
 **/
public class UserLoginReq {
    private Long id;
    @NotEmpty(message = "密码不能为空")
    private String password;
    @NotEmpty(message = "用户名不能为空")
    private String username;

    @Override
    public String toString() {
        return "UserLoginReq{" +
                "id=" + id +
                ", password='" + password + '\'' +
                ", username='" + username + '\'' +
                '}';
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
