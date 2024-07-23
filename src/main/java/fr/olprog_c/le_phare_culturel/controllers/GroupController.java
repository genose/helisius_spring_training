package fr.olprog_c.le_phare_culturel.controllers;

import fr.olprog_c.le_phare_culturel.controllers.routes.RouteDefinition;
import fr.olprog_c.le_phare_culturel.dtos.event.EventGroupSlimDTO;
import fr.olprog_c.le_phare_culturel.dtos.event.EventGroupsSlimPostRequestDTO;
import fr.olprog_c.le_phare_culturel.dtos.event.EventGroupParticipantsResponseDTO;
import fr.olprog_c.le_phare_culturel.dtos.mapper.GroupDTOMapper;
import fr.olprog_c.le_phare_culturel.entities.EventEntity;
import fr.olprog_c.le_phare_culturel.entities.EventGroupUserEntity;
import fr.olprog_c.le_phare_culturel.entities.UserEntity;
import org.springframework.http.HttpStatus;
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

    /**
     * Find groups by ID.
     *
     * @param groupid a int variable, representing the id of the group to be returned
     * @return ResponseEntity with an instance of EventGroupUserEntity wrapped in it, if the group was found,
     * otherwise ResponseEntity with an instance of null wrapped in it.
     */
    @GetMapping(RouteDefinition.Groups.GROUPS_WITH_ID_URL)
    public ResponseEntity<?> findGroupsByID(@PathVariable int groupid) {
        EventGroupUserEntity entity = groupService.findGroupById(groupid);
        return ResponseEntity.ok(groupService.convertEntityToResponseDTO(entity));
    }

    /**
     * Create new group.
     *
     * @param user    a UserEntity, representing the user creating the group
     * @param body    a EventGroupsSlimPostRequestDTO object, carrying the details about the group to be created
     * @param eventid a int variable, representing the id of the event the new group will be related to
     * @return EventGroupSlimDTO object, carrying the new group's details, if the group was created
     * otherwise return null.
     */
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

    /**
     * Add user to group.
     *
     * @param user    a UserEntity, representing the user being added to the group
     * @param eventid a int variable, representing the id of the event the group is related to
     * @param groupid a int variable, representing the id of the group the user will be added to
     * @return EventGroupParticipantsResponseDTO object, carrying updated group's details, if user was added
     * to the group, otherwise return null.
     */
    @PutMapping(RouteDefinition.Events.EVENTS_WITH_ID_GROUP_WITH_ID_USERS_URL)
    public EventGroupParticipantsResponseDTO addUserToGroupID(
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

    /**
     * Delete user from group.
     *
     * @param user    a UserEntity, representing the user being deleted from the group
     * @param eventid a int variable, representing the id of the event the group is related to
     * @param groupid a int variable, representing the id of the group the user will be deleted from
     * @return EventGroupParticipantsResponseDTO object, carrying updated group's details, if user was deleted
     * from the group, otherwise return null.
     */
    @DeleteMapping(RouteDefinition.Events.EVENTS_WITH_ID_GROUP_WITH_ID_USERS_URL)
    public EventGroupParticipantsResponseDTO deleteUserToGroupID(
            @AuthenticationPrincipal UserEntity user,
            @PathVariable int eventid,
            @PathVariable int groupid
    ) {
        EventGroupUserEntity groupUser = groupService.findGroupById(groupid);
        if (groupUser != null) {
            groupUser.getReferencedUserList().remove(user);
            EventGroupUserEntity groupUserSaved = groupService.saveEntity(groupUser);
            if (groupUserSaved != null) {
                EventGroupParticipantsResponseDTO entityToResponseDTO =
                        groupService::convertEntityToResponseDTO(groupUserSaved);
                return entityToResponseDTO;
            }
        }
        return null;
    }

    @DeleteMapping(RouteDefinition.Events.EVENTS_WITH_ID_GROUP_WITH_ID_URL)
            public ResponseEntity<?> deleteGroup(
            @AuthenticationPrincipal UserEntity user,
            @PathVariable int eventid,
            @PathVariable int groupid
    )
    {
        // nope just SET tbis damn Capture the FLAG
        EventGroupUserEntity groupUser = groupService.findGroupById(groupid);
        if (groupUser != null) {
            return ResponseEntity.badRequest().body(HttpStatus.EXPECTATION_FAILED);
        }
        return ResponseEntity.badRequest().body(HttpStatus.UNPROCESSABLE_ENTITY);
    }
}
