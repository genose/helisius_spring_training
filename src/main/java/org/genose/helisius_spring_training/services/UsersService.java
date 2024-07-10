package org.genose.helisius_spring_training.services;

import jakarta.validation.constraints.PositiveOrZero;
import org.genose.helisius_spring_training.dto_mapper.UsersMapper;
import org.genose.helisius_spring_training.dtos.UsersGetResponseDTO;
import org.genose.helisius_spring_training.repositories.UsersRepository;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UsersService extends BaseServiceImpl {
    private final UsersRepository usersRepository;

    public UsersService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    /* ****** ****** ****** ****** */
    @Override
    public Iterable<UsersGetResponseDTO> findAll() {
        /* return this.mapAllEntitiesToDto(this.usersRepository,
                UsersMapper::entityToDto);

        */
        return StreamSupport.stream(usersRepository.findAll().spliterator(), false)
                .map(UsersMapper::entityToDto)
                .collect(Collectors.toList());
    }

    /* ****** ****** ****** ****** */
    @Override
    @PositiveOrZero
    public UsersGetResponseDTO findById(Integer id) {

        return usersRepository.findById(id)
                .map(UsersMapper::entityToDto)
                .orElse(null);
    }


}
