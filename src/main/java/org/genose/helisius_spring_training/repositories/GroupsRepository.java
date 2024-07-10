package org.genose.helisius_spring_training.repositories;

import org.genose.helisius_spring_training.entities.EventsGroupsUsersEntity;
import org.genose.helisius_spring_training.entities.UsersEntity;
import org.springframework.data.repository.CrudRepository;


public interface GroupsRepository extends BaseRepository<UsersEntity, Integer> {
}
