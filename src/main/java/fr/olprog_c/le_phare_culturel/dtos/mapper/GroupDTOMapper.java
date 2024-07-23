package fr.olprog_c.le_phare_culturel.dtos.mapper;

import java.util.Collection;
import java.util.List;

import fr.olprog_c.le_phare_culturel.dtos.event.*;
import fr.olprog_c.le_phare_culturel.dtos.user.UserSlimResponseDTO;
import fr.olprog_c.le_phare_culturel.entities.EventGroupUserEntity;
import fr.olprog_c.le_phare_culturel.entities.EventGroupUserMessageEntity;
import fr.olprog_c.le_phare_culturel.entities.UserEntity;
import fr.olprog_c.le_phare_culturel.models.event.EventGroupModelDTO;

public class GroupDTOMapper {
    // public static EventGroupParticipantsResponseDTO
    // groupUserToParticipantsGroupDTO(EventGroupUserEntity group,
    // List<UserEntity> participants, List<EventMessageDTO> messages) {
    // new EventGroupParticipantsResponseDTO(
    // group.getId(),
    // group.getGroupName(),
    // group.getReferencedGroupsMessagesId().size(),
    //
    // null);
    //
    // return null;
    // }
    public static EventGroupUserEntity convertGroupRequestDTOToEventGroupUserEntity(EventGroupModelDTO eventGroupModelDTO) {
        List<UserSlimResponseDTO> participantsList = eventGroupModelDTO.participants();
        /*
         Long id,
            @JsonProperty("group_name") String groupName,
            @JsonProperty("time_meet") Instant timeMeet,
            @JsonProperty("group_size") int groupMaxSize,
            String description,
            UserResponseDTO author,
            List<UserSlimResponseDTO> participants,
            List<EventMessageDTO> messages
         */
        return new EventGroupUserEntity(
                eventGroupModelDTO.id(),
                eventGroupModelDTO.groupName(),
                eventGroupModelDTO.groupMaxSize(),
                eventGroupModelDTO.timeMeet(),
                eventGroupModelDTO.description(),
                UserDTOMapper::convertUserResponseDtoUserEntity (eventGroupModelDTO.author()),
                eventGroupModelDTO.participants(),
                eventGroupModelDTO.messages()
        );

    }

public static UserEntity convertEventGroupModelDTOToUserEntity(){

}
    public static EventGroupParticipantsResponseDTO convertGroupEntityToEventGroupParticipantsResponseDTO(EventGroupUserEntity eventGroupUser) {

        Collection<EventGroupUserMessageEntity> messagesEntitiesList = eventGroupUser.getReferencedGroupsMessages();

        List<EventMessageSlimDTO> messages = messagesEntitiesList.stream()
                .map(EventGroupUserMessageMapper::toSlimDTO)
                .toList();

        List<UserSlimResponseDTO> participantsList = messagesEntitiesList.stream()
                .map(EventGroupUserMessageMapper::toSlimDTO)
                .map(EventMessageSlimDTO::author)
                .distinct().toList();

        return new EventGroupParticipantsResponseDTO(
                eventGroupUser.getId(),
                eventGroupUser.getGroupName(),
                eventGroupUser.getTimeMeet(),
                eventGroupUser.getGroupMaxSize(),
                eventGroupUser.getDescription(),
                UserDTOMapper.responseSlimDTO(eventGroupUser.getReferencedUserAuthor()),
                participantsList,
                messages);

    }


}
