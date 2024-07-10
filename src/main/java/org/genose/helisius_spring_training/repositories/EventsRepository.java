package org.genose.helisius_spring_training.repositories;

import org.genose.helisius_spring_training.entities.EventsEntity;
import org.genose.helisius_spring_training.entities.UsersEntity;
import org.springframework.data.repository.CrudRepository;

public interface EventsRepository extends BaseRepository<UsersEntity, Integer> {
}
