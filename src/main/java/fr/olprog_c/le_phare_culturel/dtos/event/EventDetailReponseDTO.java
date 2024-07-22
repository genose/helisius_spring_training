package fr.olprog_c.le_phare_culturel.dtos.event;

import com.fasterxml.jackson.annotation.JsonProperty;

public record EventDetailReponseDTO(
    Long uid,
    EventImagesDTO images,
    String description,
    String longDescription,
    String title,
    EventLocationPlaceDTO location,
    String tarifs,
    String dateRange,
    @JsonProperty("image_credits") String imageCredits,
    @JsonProperty("first_timing") EventDateDetailsDTO firstTiming,
    @JsonProperty("last_timing") EventDateDetailsDTO lastTiming) {
}
