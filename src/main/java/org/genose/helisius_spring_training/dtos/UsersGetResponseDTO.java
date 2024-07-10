package org.genose.helisius_spring_training.dtos;

import java.time.LocalDateTime;

public final class UsersGetResponseDTO extends BaseGetResponseDTO {

    public UsersGetResponseDTO(int id, LocalDateTime dateCreated, LocalDateTime dateUpdated) {
        super(id, dateCreated, dateUpdated);
    }
}
