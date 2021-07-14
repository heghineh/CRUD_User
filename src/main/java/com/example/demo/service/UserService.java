package com.example.demo.service;

import com.example.demo.controller.UserRequest;
import com.example.demo.controller.UserResponse;
import com.example.demo.converter.Converter;
import com.example.demo.persistence.User;
import com.example.demo.persistence.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class UserService {

    //region Properties
    private BCryptPasswordEncoder encoder;
    private final UserRepository userRepository;
    private final Converter converter;
    //endregion

    //region Constructor
    public UserService(UserRepository userRepository, Converter converter) {
        this.userRepository = userRepository;
        this.converter = converter;
    }
    //endregion

    //region User Builder
    public User buildUser(UserRequest userRequest) {
        String convertedPassword = encoder.encode(userRequest.getPassword());
        System.out.println(convertedPassword);
        User user = new User(userRequest.getId(), userRequest.getName(),
                userRequest.getSurname(), userRequest.getAge(),
                userRequest.getEmail(), userRequest.getUsername(),
                convertedPassword);
        return user;
    }
    //endregion

    //region Public Methods (CRUD)
    public UserResponse create(UserRequest userRequest) {
        User user = buildUser(userRequest);
        userRepository.save(user);
        UserResponse userResponse = converter.convertFromUser(user);
        return userResponse;
    }

    public UserResponse getById(Long id) {
        User user = userRepository.getById(id);
        UserResponse userResponse = converter.convertFromUser(user);
        return userResponse;
    }

    public List<UserResponse> getAll() {
        List<User> users = userRepository.findAll();
        Stream<User> stream = users.stream();

        List<UserResponse> userResponses = stream
                .map(converter::convertFromUser)
                .collect(Collectors.toList());

        return userResponses;
    }

    public UserResponse update(Long id, UserRequest userRequest) {
        User user = buildUser(userRequest);
        User updatedUser = userRepository.getById(id);

        updatedUser.setName(user.getName());
        updatedUser.setSurname(user.getSurname());
        updatedUser.setAge(user.getAge());
        updatedUser.setEmail(user.getEmail());
        updatedUser.setUsername(user.getUsername());
        updatedUser.setPassword(updatedUser.getPassword());

        return converter.convertFromUser(userRepository.save(updatedUser));
    }

    public boolean delete(Long id) {
        userRepository.deleteById(id);
        return userRepository.existsById(id);
    }
    //endregion
}