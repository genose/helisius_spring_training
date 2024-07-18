package org.genose.helisius_spring_training.dtos.authentification;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import org.genose.helisius_spring_training.dtos.BasePostRequestDTO;

@Setter
@Getter
public final class AuthentificationLoginPostDTO extends BasePostRequestDTO {
    @Email
    @NotNull
    String email;

    @NotNull
    @Pattern(regexp = "^(?=.*\\d)(?=.*[A-Z])(?=.*[a-z])(?=.*[^\\w\\d\\s:])([^\\s]){8,64}$")
    String password;

}
