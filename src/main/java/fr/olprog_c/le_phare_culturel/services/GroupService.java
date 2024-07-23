package fr.olprog_c.le_phare_culturel.services;

import java.util.List;
import java.util.Optional;

import fr.olprog_c.le_phare_culturel.dtos.event.EventGroupsSlimPostRequestDTO;
import fr.olprog_c.le_phare_culturel.dtos.event.EventParticipantsGroupDTO;
import fr.olprog_c.le_phare_culturel.dtos.mapper.GroupDTOMapper;
import org.springframework.stereotype.Service;

import fr.olprog_c.le_phare_culturel.entities.EventGroupUserEntity;
import fr.olprog_c.le_phare_culturel.repositories.EventRepository;
import fr.olprog_c.le_phare_culturel.repositories.GroupRepository;

@Service
public class GroupService {

    private final GroupRepository groupRepository;
    private final EventRepository eventRepository;

    public GroupService(GroupRepository groupRepository, EventRepository eventRepository) {
        this.groupRepository = groupRepository;
        this.eventRepository = eventRepository;
    }

    public List<EventGroupUserEntity> findAll() {
        return groupRepository.findAll();
    }

    public EventGroupUserEntity findGroupById(long groupid) {
        return groupRepository.findBy_Uid(groupid);
    }

    public List<EventGroupUserEntity> findAllGroupsByRelatedEventId(long eventid) {
        return groupRepository.findByRelatedEvents_Uid(eventid);
    }

    public EventGroupUserEntity convertDTOToEntity(EventGroupsSlimPostRequestDTO body) {
        EventGroupUserEntity groupUserEntity = new EventGroupUserEntity();
        groupUserEntity.setGroupName(body.groupName());
        groupUserEntity.setTimeMeet(body.timeMeet());
        groupUserEntity.setDescription(body.description());
        // groupUserEntity.setReferencedUserAuthor(body.author());
        return groupUserEntity;
    }

    public EventGroupUserEntity saveEntity(EventGroupUserEntity groupUserEntityToSave) {
        Optional<EventGroupUserEntity> entity = Optional.of(this.groupRepository.save(groupUserEntityToSave));
        if(entity.isPresent())
        {
            return entity.get();
        }
        return null;
    }

    public EventParticipantsGroupDTO convertEntityToResponseDTO(EventGroupUserEntity newGroupEntity) {
        EventParticipantsGroupDTO responseDTO = GroupDTOMapper::convertGroupEntityToSlimDTO(newGroupEntity);

        return responseDTO;
    }
}
