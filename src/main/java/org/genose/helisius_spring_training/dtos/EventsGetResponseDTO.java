package org.genose.helisius_spring_training.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

// Cf dossier NOTES pour les formats

public final class EventsGetResponseDTO extends BaseGetResponseDTO {

    public EventsGetResponseDTO(int id, LocalDateTime dateCreated, LocalDateTime dateUpdated) {
        super(id, dateCreated, dateUpdated);
    }
}
