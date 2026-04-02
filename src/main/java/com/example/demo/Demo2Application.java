package com.example.demo;

import com.example.demo.entities.Address;
import com.example.demo.entities.Profile;
import com.example.demo.entities.User;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Demo2Application {

    public static void main(String[] args) {
        var user = User.builder()
                .name("John")
                .email("john@example.com")
                .password("password")
                .build();

        var address = Address.builder()
                .street("street")
                .city("city")
                .state("state")
                .zip("zip")
                .build();

        user.addTag("tag1");

        var profile = Profile.builder()
                .bio("profile")
                .build();

        user.addProfile(profile);

        user.addAddress(address);
        System.out.println(user);
    }


//    public static void main(String[] args) {
//        SpringApplication.run(Demo2Application.class, args);
//    }

}
