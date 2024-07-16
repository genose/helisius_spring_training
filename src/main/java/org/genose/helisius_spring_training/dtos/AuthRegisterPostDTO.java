package org.genose.helisius_spring_training.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;
import java.util.Map;

public final class AuthRegisterPostDTO extends BasePostRequestDTO {
        @Email @NotNull String email;

        @NotNull @Pattern(regexp = "^(?=.*\\d)(?=.*[A-Z])(?=.*[a-z])(?=.*[^\\w\\d\\s:])([^\\s]){8,64}$") String password;

        @JsonProperty("profile_nickname") @NotNull @Pattern(regexp = "^\\p{L}[\\p{L}\\d]{5,31}$", flags = Pattern.Flag.UNICODE_CASE) String profileNickname;

        @JsonProperty("profile_description") @NotNull @Length(min = 10, max = 320) String profileDescription;

        @NotNull @Pattern(regexp = "^\\p{L}[\\p{L}\\s\\-]{1,55}$", flags = Pattern.Flag.UNICODE_CASE) String firstname;

        @NotNull @Pattern(regexp = "^\\p{L}[\\p{L}\\s\\-]{1,55}$", flags = Pattern.Flag.UNICODE_CASE) String lastname;

        String avatar;

    public AuthRegisterPostDTO(int Id, Map<String, Object> datas) {
        super(Id, datas);
    }
}
