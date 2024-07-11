package org.genose.helisius_spring_training.repositories;

import org.genose.helisius_spring_training.entities.UsersEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UsersRepository  extends BaseRepository<UsersEntity, Integer> {

    Optional<UsersEntity> findByUsername(String username);
    Optional<UsersEntity> findByEmail(String email);
}
