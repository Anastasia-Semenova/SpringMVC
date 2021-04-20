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
public class FormDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;

    public static FormDto from(User user) {
        return FormDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .password(user.getHashPassword())
                .build();
    }

    public static List<FormDto> from(List<User> people) {
        return people.stream()
                .map(FormDto::from)
                .collect(Collectors.toList());
    }
}
