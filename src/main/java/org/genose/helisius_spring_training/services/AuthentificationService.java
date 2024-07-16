package org.genose.helisius_spring_training.services;

import org.genose.helisius_spring_training.repositories.UserRepository;
import org.genose.helisius_spring_training.utils.GNSClassStackUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthentificationService extends BaseServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    public AuthentificationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /* ****** ****** ****** ****** */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        this.logger.info(GNSClassStackUtils.getEnclosingMethodObject(this) + " :: " + username);
        if (username == null || username.isEmpty()) {
            this.logger.error("{} :: Username is NULL ", GNSClassStackUtils.getEnclosingMethodObject(this));
            throw new UsernameNotFoundException("username is null");
        }
        return this.userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("username is not found"));
    }
}
