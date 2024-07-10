package org.genose.helisius_spring_training.dtos;

import java.time.LocalDateTime;

public class GroupsGetRequestDTO extends BaseGetResponseDTO {
    public GroupsGetRequestDTO(int id, LocalDateTime dateCreated, LocalDateTime dateUpdated) {
        super(id, dateCreated, dateUpdated);
    }
}
