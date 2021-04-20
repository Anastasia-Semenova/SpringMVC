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
public class RegistrationFormDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String name;
    private String email;
    private String hashPassword;


    public static RegistrationFormDto from(User user) {
        return RegistrationFormDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .hashPassword(user.getHashPassword())
                .build();
    }

    public static List<FormDto> from(List<User> people) {
        return people.stream()
                .map(FormDto::from)
                .collect(Collectors.toList());
    }
}
