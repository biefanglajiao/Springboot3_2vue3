package com.example.springboot3_2vue3.domain.user;

import org.springframework.stereotype.Component;

/**
 * @Author: 常兆海
 * @Description:
 * @DateTime: 2023/6/10 18:56
 **/
@Component
public class User {
    private Long id;



    private String password;
    private String username;



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
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", password='" + password + '\'' +
                ", username='" + username + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
