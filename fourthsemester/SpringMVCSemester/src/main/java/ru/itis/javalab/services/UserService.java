package ru.itis.javalab.services;

import ru.itis.javalab.dto.UserDto;
import ru.itis.javalab.models.User;

import java.util.List;

public interface UserService {
    void addUser(UserDto userDto);

    List<UserDto> getAllUsers();

    List<UserDto> getAllUsers(int page, int size);


    void saveUser(User user);

    void ban(Long id);
}
