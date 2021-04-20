package ru.itis.javalab.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.javalab.models.User;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String hashPassword;
    private String confirmCode;
    private User.State state;
    private User.Role role;

    public static UserDto from(User user) {
        if (user == null) {
            return null;
        }
        return UserDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .hashPassword(user.getHashPassword())
                .confirmCode(user.getConfirmCode())
                .state(user.getState())
                .role(user.getRole())
                .build();
    }

    public static List<UserDto> from(List<User> people) {
        return people.stream()
                .map(UserDto::from)
                .collect(Collectors.toList());
    }
}
