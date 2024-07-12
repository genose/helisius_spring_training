package org.genose.helisius_spring_training.dtos;


import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Setter
@Getter
public final class UsersPostRequestDTO extends BasePostRequestDTO {
    private String email;
    private String password;

    public UsersPostRequestDTO(int Id, Map<String, Object> datas) {
        super(Id, datas);
    }
}
