package org.genose.helisius_spring_training.services;

import org.genose.helisius_spring_training.dtos.UserGetProfileDTO;
import org.genose.helisius_spring_training.entities.UserEntity;
import org.genose.helisius_spring_training.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService extends BaseServiceImpl {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /* ****** ****** ****** ****** */
    @Override
    public Iterable<UserGetProfileDTO> findAll() {
        return super.fetchAllEntitiesAndConvertToDto(
                UserEntity.class,
                UserGetProfileDTO.class,
                this.userRepository.getClass());
        /*
        ..... EQUALS TO ....
        List<UsersEntity> entityList = (List<UsersEntity>) this.usersRepository.findAll();
        return entityList.stream()
                .map(entity -> (UsersGetResponseDTO) BaseMapperEntity
                        .convertFromEntityToDTO(entity, UsersGetResponseDTO.class)).toList(); */
    }

    /* ****** ****** ****** ****** */
    public Optional<UserGetProfileDTO> findById(Integer id) {
        return super.findById(
                id,
                UserEntity.class,
                UserGetProfileDTO.class,
                userRepository
        );
    }
}
