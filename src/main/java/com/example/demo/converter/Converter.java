package com.example.demo.converter;

import com.example.demo.controller.UserResponse;
import com.example.demo.persistence.User;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Converter {
    //region Properties
    private UserResponse userResponse;
    //endregion

    //region Public Methods
    public UserResponse convertFromUser(User user) {
        userResponse = new UserResponse(user.getId(), user.getName(),
                user.getSurname(), user.getAge(),
                userResponse.getEmail(), user.getUsername(), user.getPassword());
        return userResponse;
    }
    //endregion

    //regionToString
    @Override
    public String toString() {
        return "Converter{" +
                "userResponse=" + userResponse +
                '}';
    }
    //endregion
}
