package fr.olprog_c.le_phare_culturel.dtos.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import fr.olprog_c.le_phare_culturel.dtos.event.EventDetailReponseWithoutGroupDTO;
import fr.olprog_c.le_phare_culturel.dtos.event.EventGroupParticipantsResponseDTO;

import java.util.List;

public record UserResponseDTO(
        @JsonProperty("firstname") String firstName,

        @JsonProperty("lastname") String lastName,

        @JsonProperty("nickname") String profileNickname,

        @JsonProperty("email") String email,

        @JsonProperty("avatar") String avatar,

        @JsonProperty("description") String profileDescription,

        @JsonProperty("groups") List<EventGroupParticipantsResponseDTO> groupsList,

        @JsonProperty("events_past") List<EventDetailReponseWithoutGroupDTO> eventsPast,

        @JsonProperty("events_current") List<EventDetailReponseWithoutGroupDTO> eventsCurrent,

        @JsonProperty("events_future") List<EventDetailReponseWithoutGroupDTO> eventsFuture

) {
    public void eventsFuture(List<EventDetailReponseWithoutGroupDTO> futureEventsListDTO) {
    }
}
