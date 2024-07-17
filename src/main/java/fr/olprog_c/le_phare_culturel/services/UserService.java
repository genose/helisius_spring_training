package fr.olprog_c.le_phare_culturel.services;

import fr.olprog_c.le_phare_culturel.dtos.AuthLoginPostResponseDTO;
import fr.olprog_c.le_phare_culturel.dtos.user.UserPostRequestAvatarDTO;
import fr.olprog_c.le_phare_culturel.dtos.user.UserPostRequestPasswordDTO;
import fr.olprog_c.le_phare_culturel.dtos.mapper.AuthDTOMapper;
import fr.olprog_c.le_phare_culturel.entities.UserEntity;
import fr.olprog_c.le_phare_culturel.repositories.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Properties;

@Service
public class UserService {
    private final UserRepository userRepository;
    /* ****** ****** utile Object.andThen(save()) ****** ****** */
    private static UserEntity userEntity;
    /* ****** ****** ****** ****** */
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /* ****** ****** ****** ****** */
    public AuthLoginPostResponseDTO convertEntityToResponseDTO(UserEntity user) {
        return AuthDTOMapper.responseDTO(user);
    }
    /* ****** ****** ****** ****** */
    public UserService convertPasswordDtoToEntity(UserPostRequestPasswordDTO body, UserEntity user) {
        userEntity = user;

        if (Objects.equals(AuthService.passwordEncoding(body.newPassword()), user.getPassword())) {
            throw new BadCredentialsException("l ancien et le nouveau Mot de passe sont identiques");
        }
        if ((body.newPasswordConfirmation() != null && !body.newPasswordConfirmation().isEmpty())
                && (body.newPassword() != null && !body.newPassword().isEmpty())
                && (body.newPassword().equals(body.newPasswordConfirmation()))
        ) {

            user.setEmail(body.email());
            user.setPassword(AuthService.passwordEncoding(body.newPassword()));
            // user.setUpdatedDate(LocalDateTime.now());
        } else {
            throw new BadCredentialsException("Mot de passe de remplacement different");
        }

        return this;
    }

    /* ****** ****** ****** ****** */
    public UserService convertAvatarDtoToEntity(UserPostRequestAvatarDTO body, UserEntity user) {
        userEntity = user;

        if (body.avatar().isEmpty()) {
            throw new BadCredentialsException("avatar is empty");
        }
        user.setAvatar(body.avatar());
        // user.setUpdatedDate(LocalDateTime.now());
        return this;
    }

    /* ****** ****** ****** ****** */
    public boolean save() {
        boolean saved = userRepository.save(userEntity) != null;
        userEntity = null;
        return saved;
    }
}
