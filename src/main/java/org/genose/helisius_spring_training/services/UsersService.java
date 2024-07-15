package org.genose.helisius_spring_training.services;

import org.genose.helisius_spring_training.dtos.UsersGetResponseDTO;
import org.genose.helisius_spring_training.entities.UsersEntity;
import org.genose.helisius_spring_training.mapper.BaseMapperEntity;
import org.genose.helisius_spring_training.repositories.UsersRepository;
import org.genose.helisius_spring_training.utils.GNSClassStackUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsersService extends BaseServiceImpl implements UserDetailsService {
    private final UsersRepository usersRepository;

    public UsersService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    /* ****** ****** ****** ****** */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        this.logger.info(GNSClassStackUtils.getEnclosingMethodObject(this) + " :: " + username);
        if (username == null || username.isEmpty()) {
            this.logger.error("{} :: Username is NULL ", GNSClassStackUtils.getEnclosingMethodObject(this));
            throw new UsernameNotFoundException("username is null");
        }
        return this.usersRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("username is not found"));
    }

    /* ****** ****** ****** ****** */
    @Override
    public Iterable<UsersGetResponseDTO> findAll() {
        List<UsersEntity> entityList = (List<UsersEntity>) this.usersRepository.findAll();
        return entityList.stream()
                .map(entity -> (UsersGetResponseDTO) BaseMapperEntity.convertFromEntityToDTO(entity, UsersGetResponseDTO.class)).toList();
    }

    /* ****** ****** ****** ****** */
    @Override
    public Optional<UsersGetResponseDTO> findById(Integer id) {
        Optional<UsersEntity> usersEntityOptional = usersRepository.findById(id);
        if (usersEntityOptional.isPresent()) {
            UsersEntity entityObject = usersEntityOptional.get();
            UsersGetResponseDTO responseDTO = (UsersGetResponseDTO) BaseMapperEntity.convertFromEntityToDTO(entityObject, UsersGetResponseDTO.class);
            return Optional.of(responseDTO);
        }
        return null;
    }
}
