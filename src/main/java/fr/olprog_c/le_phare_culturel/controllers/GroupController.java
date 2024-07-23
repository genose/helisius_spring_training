package fr.olprog_c.le_phare_culturel.controllers;

import fr.olprog_c.le_phare_culturel.controllers.routes.RouteDefinition;
import fr.olprog_c.le_phare_culturel.dtos.event.EventGroupSlimDTO;
import fr.olprog_c.le_phare_culturel.dtos.event.EventGroupsSlimPostRequestDTO;
import fr.olprog_c.le_phare_culturel.dtos.event.EventParticipantsGroupDTO;
import fr.olprog_c.le_phare_culturel.dtos.mapper.GroupDTOMapper;
import fr.olprog_c.le_phare_culturel.entities.EventEntity;
import fr.olprog_c.le_phare_culturel.entities.EventGroupUserEntity;
import fr.olprog_c.le_phare_culturel.entities.UserEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import fr.olprog_c.le_phare_culturel.repositories.EventRepository;
import fr.olprog_c.le_phare_culturel.services.GroupService;

import java.util.List;
import java.util.Optional;

@RestController
public class GroupController {

    private final GroupService groupService;
    private final EventRepository eventRepository;

    public GroupController(GroupService groupService, EventRepository eventRepository) {
        this.groupService = groupService;
        this.eventRepository = eventRepository;
    }

  /*@GetMapping(RouteDefinition.Events.EVENTS_WITH_ID_GROUP_LIST_URL)
  public ResponseEntity<?> findAllGroupsByEventID(@PathVariable int eventid){
    List<EventGroupUserEntity> entityList = groupService.findAllGroupsByRelatedEventId(eventid);
    return ResponseEntity.ok( entityList.stream().map(groupService::convertEntityToResponseDTO).toList());
  }*/

    @GetMapping(RouteDefinition.Groups.GROUPS_WITH_ID_URL)
    public ResponseEntity<?> findGroupsByID(@PathVariable int groupid) {
        EventGroupUserEntity entity = groupService.findGroupById(groupid);
        return ResponseEntity.ok(groupService.convertEntityToResponseDTO(entity));
    }

    @PostMapping(RouteDefinition.Events.EVENTS_WITH_ID_GROUP_LIST_URL)
    public EventGroupSlimDTO createNewGroup(
            @AuthenticationPrincipal UserEntity user,
            @RequestBody EventGroupsSlimPostRequestDTO body,
            @PathVariable int eventid
    ) {
        Optional<EventEntity> eventRelated = eventRepository.findById((long) eventid);
        if (eventRelated.isPresent()) {
            EventGroupUserEntity newGroupUserEntity = groupService.convertDTOToEntity(body);
            newGroupUserEntity.setReferencedUserAuthor(user);
            newGroupUserEntity.setRelatedEvents(eventRelated.get());
            EventGroupUserEntity newGroupEntity = groupService.saveEntity(newGroupUserEntity);

            if (newGroupEntity != null) {
                return groupService.convertEntityToResponseDTO(newGroupEntity);
            }
        }
        return null;
    }

    @PutMapping(RouteDefinition.Events.EVENTS_WITH_ID_GROUP_WITH_ID_URL)
    public EventParticipantsGroupDTO addUserToGroupID(
            @AuthenticationPrincipal UserEntity user,
            @PathVariable int eventid,
            @PathVariable int groupid
    ) {
        EventGroupUserEntity groupUser = groupService.findGroupById(groupid);
        if (groupUser != null) {
            groupUser.getReferencedUserList().add(user);
            EventGroupUserEntity groupUserSaved = groupService.saveEntity(groupUser);
            if (groupUserSaved != null) {
                return groupService::convertEntityToResponseDTO (groupUserSaved);
            } ;
        }
        return null;
    }

    @DeleteMapping(RouteDefinition.Events.EVENTS_WITH_ID_GROUP_WITH_ID_URL)
    public EventParticipantsGroupDTO deleteUserToGroupID(
            @AuthenticationPrincipal UserEntity user,
            @PathVariable int eventid,
            @PathVariable int groupid
    ) {
        EventGroupUserEntity groupUser = groupService.findGroupById(groupid);
        if (groupUser != null) {
            groupUser.getReferencedUserList(). (user);
            EventGroupUserEntity groupUserSaved = groupService.saveEntity(groupUser);
            if (groupUserSaved != null) {
                return groupService::convertEntityToResponseDTO (groupUserSaved);
            } ;
        }
        return null;
    }

}
