package fr.olprog_c.le_phare_culturel.dtos.mapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import fr.olprog_c.le_phare_culturel.dtos.event.*;
import fr.olprog_c.le_phare_culturel.dtos.user.UserMessageResponseDTO;
import fr.olprog_c.le_phare_culturel.dtos.user.UserSlimResponseDTO;
import fr.olprog_c.le_phare_culturel.entities.EventGroupUserEntity;
import fr.olprog_c.le_phare_culturel.entities.EventGroupUserMessageEntity;
import fr.olprog_c.le_phare_culturel.entities.UserEntity;

public class GroupDTOMapper {
  // public static EventParticipantsGroupDTO
  // groupUserToParticipantsGroupDTO(EventGroupUserEntity group,
  // List<UserEntity> participants, List<EventMessageDTO> messages) {
  // new EventParticipantsGroupDTO(
  // group.getId(),
  // group.getGroupName(),
  // group.getReferencedGroupsMessagesId().size(),
  //
  // null);
  //
  // return null;
  // }
    public static EventGroupUserEntity convertGroupRequestDTOToEntity(EventGroupDTO entity )
    {
        List<UserSlimResponseDTO> participantsList = entity.participants();

        return new EventParticipantsGroupDTO(
                entity.id(),
                entity.groupName(),
                participantsList.size(),
                entity.messages()
        );

    }

    public static EventParticipantsGroupDTO convertGroupEntityToSlimDTO(EventGroupUserEntity eventGroupUser) {

        Collection<EventGroupUserMessageEntity> messagesEntitiesList = eventGroupUser.getReferencedGroupsMessages();
        List<EventMessageSlimDTO> messages = messagesEntitiesList.stream()
                .map(EventGroupUserMessageMapper::toSlimDTO)
                .toList();

        List<UserSlimResponseDTO> participantsList = messagesEntitiesList.stream()
                .map(EventGroupUserMessageMapper::toSlimDTO)
                .map(EventMessageSlimDTO::author)
                .distinct().toList();

        return new EventParticipantsGroupDTO(
                eventGroupUser.getId(),
                eventGroupUser.getGroupName(),
                eventGroupUser.getTimeMeet(),
                eventGroupUser.getDescription(),
                UserDTOMapper.responseSlimDTO(eventGroupUser.getReferencedUserAuthor()),
                participantsList,
                messages);

    }


}
