package com.example.springboot3_2vue3.resp.user;

import jakarta.validation.constraints.NotEmpty;

/**
 * @Author: 常兆海
 * @Description:
 * @DateTime: 2023/6/10 19:06
 **/
public class UserLoginResp {
    private Long id;

    private String password;

    private String username;
    private  String token;

    @Override
    public String toString() {
        return "UserLoginResp{" +
                "id=" + id +
                ", password='" + password + '\'' +
                ", username='" + username + '\'' +
                ", token='" + token + '\'' +
                '}';
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
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
