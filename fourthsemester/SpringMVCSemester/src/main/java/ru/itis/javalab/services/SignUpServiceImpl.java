package ru.itis.javalab.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.itis.javalab.dto.RegistrationFormDto;
import ru.itis.javalab.dto.UserDto;
import ru.itis.javalab.models.User;
import ru.itis.javalab.util.EmailUtil;
import ru.itis.javalab.util.MailsGenerator;

import java.util.UUID;

@Service
public class SignUpServiceImpl implements SignUpService {

    private final PasswordEncoder passwordEncoder;

    private final UserService usersService;

    private final MailsGenerator mailsGenerator;

    private final EmailUtil emailUtil;

    @Value("${server.url}")
    private String serverUrl;

    @Value("${mail.username}")
    private String from;

    @Value("${mail.message.subject}")
    private String subject;

    public SignUpServiceImpl(MailsGenerator mailsGenerator, EmailUtil emailUtil, UserService usersService, PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
        this.mailsGenerator = mailsGenerator;
        this.emailUtil = emailUtil;
        this.usersService = usersService;
    }

    @Override
    public void signUp(RegistrationFormDto form) {

        UserDto newUser = UserDto.builder()
                .firstName(form.getFirstName())
                .lastName(form.getLastName())
                .email(form.getEmail())
                .hashPassword(passwordEncoder.encode(form.getHashPassword()))
                .confirmCode(UUID.randomUUID().toString())
                .state(User.State.NOT_CONFIRMED)
                .role(User.Role.ROLE_USER)
                .build();

        usersService.addUser(newUser);

        String confirmMail = mailsGenerator.getMailForConfirm(serverUrl, newUser.getConfirmCode());
        emailUtil.sendMail(newUser.getEmail(), subject, from, confirmMail);
    }
}

