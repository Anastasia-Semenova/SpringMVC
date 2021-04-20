package ru.itis.javalab.security.details;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import ru.itis.javalab.models.User;
import ru.itis.javalab.repositories.UsersRepository;


@Component("customUserDetailsService")
public class UsersDetailsServiceImpl implements UserDetailsService {

    private final UsersRepository usersRepository;

    public UsersDetailsServiceImpl(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user =  usersRepository.findByFirstName(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new UsersDetailsImpl(user);
    }

}
