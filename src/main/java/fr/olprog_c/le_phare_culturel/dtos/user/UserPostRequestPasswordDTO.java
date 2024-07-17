package fr.olprog_c.le_phare_culturel.dtos.user;

public record UserPostRequestPasswordDTO(
        String email,
        String password,
        String newPassword,
        String newPasswordConfirmation
) {
}
