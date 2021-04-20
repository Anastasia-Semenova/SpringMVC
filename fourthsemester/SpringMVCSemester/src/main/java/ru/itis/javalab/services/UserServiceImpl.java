package ru.itis.javalab.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.itis.javalab.dto.UserDto;
import ru.itis.javalab.models.User;
import ru.itis.javalab.repositories.UsersRepository;

import java.util.List;
import java.util.Optional;

import static ru.itis.javalab.dto.UserDto.from;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private final UsersRepository usersRepository;

    public UserServiceImpl(UsersRepository usersRepository, PasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void addUser(UserDto userDto) {
        usersRepository.save(User.builder()
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .email(userDto.getEmail())
                .hashPassword(userDto.getHashPassword())
                .confirmCode(userDto.getConfirmCode())
                .state(userDto.getState())
                .role(userDto.getRole())
                .build());
    }

    @Override
    public List<UserDto> getAllUsers() {
        return from(usersRepository.findAll());
    }

    @Override
    public List<UserDto> getAllUsers(int page, int size) {
        return from(usersRepository.findAll(PageRequest.of(page, size)).getContent());
    }



    @Override
    public void saveUser(User user) {
        usersRepository.save(user);
    }

    @Override
    public void ban(Long id) {
        Optional<User> someUser = usersRepository.findById(id);
        if (someUser.isPresent()) {
            User user = someUser.get();
            user.setState(User.State.BANNED);
            usersRepository.save(user);
        }
    }
}
