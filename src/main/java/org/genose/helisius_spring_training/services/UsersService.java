package org.genose.helisius_spring_training.services;

import org.genose.helisius_spring_training.dto_mapper.UsersMapperDTO;
import org.genose.helisius_spring_training.dtos.UsersGetResponseDTO;
import org.genose.helisius_spring_training.entities.UsersEntity;
import org.genose.helisius_spring_training.repositories.UsersRepository;
import org.genose.helisius_spring_training.utils.ClassStackUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UsersService extends BaseServiceImpl implements UserDetailsService {
    private final UsersRepository usersRepository;

    public UsersService(UsersRepository usersRepository
    ) {
        this.usersRepository = usersRepository;
    }

    /* ****** ****** ****** ****** */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        this.logger.info(ClassStackUtils.getEnclosingMethodObject(this) + " :: " + username);
        return usersRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    /* ****** ****** ****** ****** */
    @Override
    public Iterable<UsersGetResponseDTO> findAll() {
        /* return this.mapAllEntitiesToDto(this.usersRepository,
                UsersMapper::entityToDto);

        */
        return StreamSupport.stream(
                        usersRepository.findAll().spliterator()
                        , false)
                .map(UsersMapperDTO::entityToDto)
                .collect(Collectors.toList());
    }

    /* ****** ****** ****** ****** */
    @Override
    public UsersGetResponseDTO findById(Integer id) {
        Optional<UsersEntity> usersEntityOptional = usersRepository.findById(id);
        if (usersEntityOptional.isPresent()) {
            UsersEntity entityToDto = usersEntityOptional.get();

            return UsersMapperDTO::entityToDto(entityToDto) ;
        }
    }


}
