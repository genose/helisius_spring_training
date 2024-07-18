package org.genose.helisius_spring_training.dtos.authentification;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.genose.helisius_spring_training.dtos.BasePostRequestDTO;
import org.hibernate.validator.constraints.Length;

public class AuthenticationRegisterPostDTO extends BasePostRequestDTO {

    // https://stackoverflow.com/questions/65370879/javax-validation-constraints-email-in-springboot
    @NotNull
    @Email(message = "Email is not valid", regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
    @NotEmpty(message = "Email cannot be empty")
    String email;

    @NotNull
    @Pattern(regexp = "^(?=.*\\d)(?=.*[A-Z])(?=.*[a-z])(?=.*[^\\w\\d\\s:])([^\\s]){8,64}$")
    @NotEmpty(message = "password cannot be empty")
    String password;

    @NotNull
    @JsonProperty("profile_nickname")
    @Pattern(regexp = "^\\p{L}[\\p{L}\\d]{5,31}$", flags = Pattern.Flag.UNICODE_CASE)
    @NotEmpty(message = "Nickname cannot be empty")
    String profileNickname;

    @NotNull
    @JsonProperty("profile_description")
    @Length(min = 10, max = 320)
    String profileDescription;

    @NotNull
    @Pattern(regexp = "^\\p{L}[\\p{L}\\s\\-]{1,55}$", flags = Pattern.Flag.UNICODE_CASE)
    String firstname;

    @NotNull
    @Pattern(regexp = "^\\p{L}[\\p{L}\\s\\-]{1,55}$", flags = Pattern.Flag.UNICODE_CASE)
    String lastname;

    String avatar;
}
