package org.genose.helisius_spring_training.configuration;

import org.genose.helisius_spring_training.repositories.UsersRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UsersDetailsConfiguration implements UserDetailsService {
    private final UsersRepository usersRepository;

    public UsersDetailsConfiguration(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (username == null || username.isEmpty()) {
            throw new UsernameNotFoundException("username is null");
        }
        return this.usersRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("username is not found") );
    }
}
