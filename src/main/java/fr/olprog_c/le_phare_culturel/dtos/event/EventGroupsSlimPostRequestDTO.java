package fr.olprog_c.le_phare_culturel.dtos.event;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import fr.olprog_c.le_phare_culturel.dtos.user.UserSlimResponseDTO;

import java.time.Instant;

public record EventGroupsSlimPostRequestDTO (
            @JsonProperty("group_name") String groupName,
            @JsonProperty("time_meet")
            @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
            String timeMeet,

            String description,
            @JsonProperty("group_size") int groupMaxSize )
{

}