package com.example.demo.controllers;

import com.example.demo.dtos.RegisterUserRequest;
import com.example.demo.dtos.UpdateUserRequest;
import com.example.demo.dtos.UserDto;
import com.example.demo.mappers.UserMapper;
import com.example.demo.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.aspectj.weaver.ast.Var;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Set;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    @GetMapping
    public Iterable<UserDto> getAllUsers(
            @RequestParam(required = false,defaultValue = "",name = "sortBy") String sortBy
    ) {
        if (!Set.of("name", "email").contains(sortBy)) {
            sortBy = "name";
        }
        return userRepository.findAll(Sort.by(sortBy))
                .stream()
                .map(userMapper::toDto)
                .toList();

    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        var user = userRepository.findById(id).orElse(null);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(userMapper.toDto(user));
    }

    @PostMapping
    public ResponseEntity<UserDto> createUser(
            @RequestBody RegisterUserRequest request,
            UriComponentsBuilder uriBuilder) {
        var user = userMapper.toEntity(request);
        userRepository.save(user);
        var userDto = userMapper.toDto(user);
        var uri = uriBuilder.path("/users/{id}")
                .buildAndExpand(userDto.getId())
                .toUri();

        return ResponseEntity.created(uri).body(userDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(
            @PathVariable(name = "id") Long id,
            @RequestBody UpdateUserRequest request
            ){
        var user = userRepository.findById(id).orElse(null);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        userMapper.updateEntity(request,user);
        userRepository.save(user);
        return ResponseEntity.ok(userMapper.toDto(user));
    }
}
