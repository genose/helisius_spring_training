package fr.olprog_c.le_phare_culturel.dtos.event;

import java.time.Instant;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import fr.olprog_c.le_phare_culturel.dtos.user.UserResponseDTO;
import fr.olprog_c.le_phare_culturel.dtos.user.UserSlimResponseDTO;
import fr.olprog_c.le_phare_culturel.entities.UserEntity;

public record EventParticipantsGroupDTO(
        long id,
        @JsonProperty("group_name") String groupName,
        @JsonProperty("time_meet") Instant timeMeet,
        String description,
        @JsonProperty("group_size") int groupSize,
        UserSlimResponseDTO author,
        @JsonProperty("participants") List<UserSlimResponseDTO> participantList,
        @JsonProperty("messages") List<EventMessageDTO> messageList) {
}
