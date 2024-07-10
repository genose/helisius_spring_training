package org.genose.helisius_spring_training.repositories;

import org.genose.helisius_spring_training.entities.EventsKeywordsCategoryEntity;
import org.genose.helisius_spring_training.entities.UsersEntity;
import org.springframework.data.repository.CrudRepository;

public interface CategoryKeywordsRepository extends BaseRepository<UsersEntity, Integer> {
}
