package org.genose.helisius_spring_training.repositories;

import org.genose.helisius_spring_training.entities.UserEntity;

import java.util.Optional;

public interface UsersRepository extends BaseRepository<UserEntity, Integer> {

    Optional<UserEntity> findByUsername(String username);

    Optional<UserEntity> findByEmail(String email);
}
