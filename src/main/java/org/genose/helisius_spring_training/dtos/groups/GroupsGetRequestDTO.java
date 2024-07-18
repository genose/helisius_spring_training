package org.genose.helisius_spring_training.dtos.groups;

import org.genose.helisius_spring_training.dtos.BaseGetResponseDTO;
import org.genose.helisius_spring_training.entities.EventGroupMessageEntity;
import org.genose.helisius_spring_training.entities.UserEntity;

import java.util.List;

public class GroupsGetRequestDTO extends BaseGetResponseDTO {
    private Integer eventId;
    private Integer id;
    private String name;
    private String description;
    private String time;
    private List<UserEntity> members;
    private List<EventGroupMessageEntity> messages;
}
