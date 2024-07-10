package org.genose.helisius_spring_training.dtos;


import java.util.Map;

public final class UsersPostRequestDTO extends BasePostRequestDTO {

    public UsersPostRequestDTO(int Id, Map<String, Object> datas) {
        super(Id, datas);
    }
}
