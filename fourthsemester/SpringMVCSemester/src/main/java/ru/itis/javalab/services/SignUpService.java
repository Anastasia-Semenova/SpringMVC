package ru.itis.javalab.services;

import ru.itis.javalab.dto.FormDto;
import ru.itis.javalab.dto.RegistrationFormDto;

public interface SignUpService {
    void signUp(RegistrationFormDto form);
}
