package org.genose.helisius_spring_training.dtos;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Setter
@Getter
public final class UserLoginRequestDTO extends BasePostRequestDTO {
    @Email
    @NotNull
    String email;

    @NotNull
    @Pattern(regexp = "^(?=.*\\d)(?=.*[A-Z])(?=.*[a-z])(?=.*[^\\w\\d\\s:])([^\\s]){8,64}$")
    String password;

    public UserLoginRequestDTO(int Id, Map<String, Object> datas) {
        super(Id, datas);
    }
}
