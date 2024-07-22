package fr.olprog_c.le_phare_culturel.dtos.event;

import fr.olprog_c.le_phare_culturel.dtos.user.UserMessageResponseDTO;

public record EventMessageDTO(
    String text,
    UserMessageResponseDTO author) {
}
