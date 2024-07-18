package org.genose.helisius_spring_training.dtos.groups;

import org.genose.helisius_spring_training.dtos.BasePostRequestDTO;
import org.genose.helisius_spring_training.entities.UserEntity;

public final class GroupCreationRequestDTO extends BasePostRequestDTO {
    private String name;
    private String description;
    private UserEntity authorId;
}
