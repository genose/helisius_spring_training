package org.genose.helisius_spring_training.dtos.events;

import org.genose.helisius_spring_training.dtos.BaseGetResponseDTO;

import java.time.LocalDateTime;

// Cf dossier NOTES pour les formats

public final class EventsGetResponseDTO extends BaseGetResponseDTO {
    private int id;
    private String eventName;
    private String eventPic;
    private LocalDateTime eventTime;
    private LocalDateTime eventDate;
    private String eventDesc;
    private String eventCategory;
}
