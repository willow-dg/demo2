package com.example.demo;

import com.example.demo.entities.User;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Demo2Application {

    public static void main(String[] args) {
        var user=User.builder()
                .name("John")
                .email("john@example.com")
                .password("password")
                .build();
        System.out.println(user.getName());
    }

}
