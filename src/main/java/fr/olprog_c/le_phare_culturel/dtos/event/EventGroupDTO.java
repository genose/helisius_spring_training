package fr.olprog_c.le_phare_culturel.dtos.event;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import fr.olprog_c.le_phare_culturel.dtos.user.UserResponseDTO;
import fr.olprog_c.le_phare_culturel.dtos.user.UserSlimResponseDTO;

public record EventGroupDTO(
    Long id,
    @JsonProperty("group_name") String groupName,
    UserResponseDTO author,
    List<UserSlimResponseDTO> participants,
    List<EventMessageDTO> messages
) {
}
