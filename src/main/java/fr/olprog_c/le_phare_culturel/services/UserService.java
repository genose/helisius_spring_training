package fr.olprog_c.le_phare_culturel.services;

import fr.olprog_c.le_phare_culturel.dtos.event.EventDetailReponseWithoutGroupDTO;
import fr.olprog_c.le_phare_culturel.dtos.event.EventDetailSlimReponseDTO;
import fr.olprog_c.le_phare_culturel.dtos.event.EventGroupParticipantsResponseDTO;
import fr.olprog_c.le_phare_culturel.dtos.event.EventsSlimIDDto;
import fr.olprog_c.le_phare_culturel.dtos.mapper.EventDTOMapper;
import fr.olprog_c.le_phare_culturel.dtos.mapper.GroupDTOMapper;
import fr.olprog_c.le_phare_culturel.dtos.user.UserNewPasswordPutRequestDTO;
import fr.olprog_c.le_phare_culturel.dtos.mapper.UserDTOMapper;
import fr.olprog_c.le_phare_culturel.dtos.user.UserAvatarPutRequestDTO;
import fr.olprog_c.le_phare_culturel.dtos.user.UserRequestDTO;
import fr.olprog_c.le_phare_culturel.dtos.user.UserResponseDTO;
import fr.olprog_c.le_phare_culturel.entities.EventEntity;
import fr.olprog_c.le_phare_culturel.entities.EventGroupUserEntity;
import fr.olprog_c.le_phare_culturel.entities.TTimingEntity;
import fr.olprog_c.le_phare_culturel.entities.UserEntity;
import fr.olprog_c.le_phare_culturel.repositories.EventRepository;
import fr.olprog_c.le_phare_culturel.repositories.GroupRepository;
import fr.olprog_c.le_phare_culturel.repositories.UserRepository;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {
    private static UserRepository userRepository;
    /* ****** ****** utile Object.andThen(save()) ****** ****** */
    private static UserEntity userEntity;
    private final EventRepository eventRepository;
    private final GroupRepository groupRepository;

    /**
     * UserService constructor
     *
     * @param userRepository The UserRepository to be used by this service
     */
    public UserService(UserRepository userRepository, EventRepository eventRepository, GroupRepository groupRepository) {
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
        this.groupRepository = groupRepository;
    }

    /**
     * Converts a UserEntity to UserPostResponseDTO
     *
     * @param user The user to be converted
     * @return The converted UserPostResponseDTO
     * @throws BadCredentialsException If the conversion fails
     */
    public UserResponseDTO convertEntityToResponseDTO(UserEntity user) {
        try {
            UserResponseDTO userResponseDTO = null;
            /* ****** ****** ****** ****** */
            if (user != null) {
                /* ****** ****** ****** ****** */
                Optional<List<EventGroupUserEntity>> allEventGroupsForUser =
                        Optional.ofNullable(groupRepository.findAllEventGroupsByReferencedUserListUserId(user.getId()));
                /* ****** ****** ****** ****** */
                List<Long> ids = allEventGroupsForUser.orElse(Collections.emptyList()).stream()
                        .map(EventGroupUserEntity::getRelatedEvents)
                        .map(EventEntity::getUid)
                        .collect(Collectors.toList());
                /* ****** ****** ****** ****** */
                Optional<List<EventEntity>> listEventsOpt = eventRepository.findFutureEventsWithIds(ids);
                /* ****** ****** ****** ****** */
                List<EventEntity> listEvents = listEventsOpt.orElse(Collections.emptyList());
                /* ****** ****** ****** ****** */
                List<EventDetailReponseWithoutGroupDTO> futureEventsListDTO = listEvents.stream()
                        .map(EventDTOMapper::convertDetailReponseWithoutGroupDTO)
                        .toList();
                /* ****** ****** ****** ****** */
                /* ****** ****** ****** ****** */
                userResponseDTO = UserDTOMapper.responseDTO(user,
                        null,
                        null,
                        null,
                        null);

                userResponseDTO.eventsFuture(futureEventsListDTO);
                /* ****** ****** ****** ****** */
                if(allEventGroupsForUser.isPresent())
                {
                    List<EventGroupUserEntity> groupUserEntityList = allEventGroupsForUser.orElse(Collections.emptyList());
                    List<EventGroupParticipantsResponseDTO> participantsResponseDTOList = groupUserEntityList
                            .stream()
                            .map(GroupDTOMapper::convertGroupEntityToEventGroupParticipantsResponseDTO)
                            .toList();

                    userResponseDTO.groupsList(participantsResponseDTOList);
                }
            }
            /* ****** ****** ****** ****** */
            return userResponseDTO;
            /* ****** ****** ****** ****** */
        } catch (Exception e) {
            throw new BadCredentialsException(" Erreur de conversion : " + e.getMessage());
        }
    }

    /**
     * Updates a UserEntity based on a UserPostRequestDTO
     *
     * @param requestDTO The request DTO containing the new user data
     * @param user       The entity to be updated
     * @return This service, for chaining
     * @throws BadCredentialsException If the old and new password are identical or the replacement password is different
     */
    public UserService convertRequestDtoToEntity(UserRequestDTO requestDTO, UserEntity user) {

        // case of changing password
        if ((requestDTO.newPassword() != null && !requestDTO.newPassword().isEmpty())
                && (requestDTO.password() != null && !requestDTO.password().isEmpty())
        ) {
            if (Objects.equals(AuthService.passwordEncoding(requestDTO.newPassword()), user.getPassword())) {
                throw new BadCredentialsException("l ancien et le nouveau Mot de passe sont identiques");
            }
            if (requestDTO.password().equals(requestDTO.newPassword())) {
                user.setPassword(AuthService.passwordEncoding(requestDTO.newPassword()));
            } else {
                throw new BadCredentialsException("Mot de passe de remplacement different");
            }
            // user.setUpdatedDate(LocalDateTime.now());
        }
        // case of profile profile description
        if (requestDTO.firstName() != null && !requestDTO.firstName().isEmpty()) {
            user.setFirstName(requestDTO.firstName());
        }
        if (requestDTO.lastName() != null && !requestDTO.lastName().isEmpty()) {
            user.setLastName(requestDTO.lastName());
        }
        if (requestDTO.profileDescription() != null && !requestDTO.profileDescription().isEmpty()) {
            user.setProfileDescription(requestDTO.profileDescription());
        }
        if (requestDTO.email() != null && !requestDTO.email().isEmpty()) {
            user.setEmail(requestDTO.email());
        }
        if (requestDTO.profileNickname() != null && !requestDTO.profileNickname().isEmpty()) {
            user.setProfileNickname(requestDTO.profileNickname());
        }
        if (requestDTO.avatar() != null && !requestDTO.avatar().isEmpty()) {
            user.setAvatar(requestDTO.avatar());

        }

        userEntity = user;
        return this;
    }

    /**
     * Convert UserAvatarPutRequestDTO to UserEntity
     *
     * @param dto  The UserAvatarPutRequestDTO containing updated avatar URL
     * @param user The UserEntity to be updated
     * @return This UserService instance for chaining
     */
    public UserService convertRequestAvatarDtoToEntity(UserAvatarPutRequestDTO dto, UserEntity user) {
        System.out.println(dto);
        if (dto.url() != null && !dto.url().isEmpty()) {
            user.setAvatar(dto.url());
        }

        this.userEntity = user;
        return this;
    }

    /**
     * Update the UserEntity based on UserNewPasswordPutRequestDTO
     *
     * @param requestDTO The request DTO containing the new password data
     * @param user       The UserEntity to be updated
     * @return This UserService instance for chaining
     * @throws BadCredentialsException If the old and new password are identical, or if the confirmation password doesn't match the new password
     */
    public UserService convertRequestNewPasswordDtoToEntity(UserNewPasswordPutRequestDTO requestDTO, UserEntity user) {

        if ((requestDTO.newPassword() != null && !requestDTO.newPassword().isEmpty())
                && (requestDTO.password() != null && !requestDTO.password().isEmpty())
        ) {
            if (Objects.equals(AuthService.passwordEncoding(requestDTO.newPassword()), user.getPassword())) {
                throw new BadCredentialsException("l ancien et le nouveau Mot de passe sont identiques");
            }
            if (requestDTO.password().equals(requestDTO.newPassword())) {
                user.setPassword(AuthService.passwordEncoding(requestDTO.newPassword()));
            } else {
                throw new BadCredentialsException("Mot de passe de remplacement different");
            }
            // user.setUpdatedDate(LocalDateTime.now());
        }

        this.userEntity = user;
        return this;
    }

    /**
     * Saves the UserEntity to the database
     *
     * @return True if the user was successfully saved, false otherwise
     */
    public boolean save() {
        Optional<UserEntity> existingUserEntity = userRepository.findById(userEntity.getId());
        boolean saved = false;
        if (existingUserEntity.isPresent()) {
            UserEntity savedEntity = userRepository.save(this.userEntity);
            if (existingUserEntity.get().equals(savedEntity)) {
                saved = true;
            }
        }
        return saved;
    }

}
