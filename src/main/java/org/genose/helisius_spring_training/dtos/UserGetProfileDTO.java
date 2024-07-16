package org.genose.helisius_spring_training.dtos;

import java.time.LocalDateTime;

public class UserGetProfileDTO extends BaseGetResponseDTO {

    public UserGetProfileDTO(int id) {
        super(id);
    }

    public UserGetProfileDTO(int id, LocalDateTime dateCreated, LocalDateTime dateUpdated) {
        super(id, dateCreated, dateUpdated);
    }
}
