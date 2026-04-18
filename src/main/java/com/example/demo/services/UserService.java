package com.example.demo.services;

import com.example.demo.entities.Address;
import com.example.demo.entities.User;
import com.example.demo.repositories.ProfileRepository;
import com.example.demo.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;

    @Transactional
    public void showEntityStates() {
        var user = User.builder()
                .name("name")
                .password("password")
                .email("email")
                .build();
        userRepository.save(user);
    }

    @Transactional
    public void showEntityStrategy() {
        var profile = profileRepository.findById(4L).orElseThrow();
        System.out.println(profile.getUser().getName());
    }

    public void persistRelated() {
        var user = User.builder()
                .name("name")
                .password("password")
                .email("email")
                .build();
        var address = Address.builder()
                .street("street")
                .city("city")
                .state("state")
                .zip("zip")
                .build();
        user.addAddress(address);
        userRepository.save(user);
    }

    @Transactional
    public void removeRelated() {
        var user = userRepository.findById(13L).orElseThrow();
        var address = user.getAddresses().getFirst();
        user.removeAddress(address);
        userRepository.save(user);
    }

}
