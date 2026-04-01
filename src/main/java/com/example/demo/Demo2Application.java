package com.example.demo;

import com.example.demo.entities.Address;
import com.example.demo.entities.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Demo2Application {

    public static void main(String[] args) {
        var user=User.builder()
                .name("John")
                .email("john@example.com")
                .password("password")
                .build();

        var address= Address.builder()
                .street("street")
                .city("city")
                .state("state")
                .zip("zip")
                .build();

        user.addAddress(address);
        System.out.println(user);
    }


//    public static void main(String[] args) {
//        SpringApplication.run(Demo2Application.class, args);
//    }

}
