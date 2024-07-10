package org.genose.helisius_spring_training.dto_mapper;

import org.genose.helisius_spring_training.dtos.UsersGetResponseDTO;
import org.genose.helisius_spring_training.dtos.UsersPostRequestDTO;
import org.genose.helisius_spring_training.entities.UsersEntity;
import org.genose.helisius_spring_training.mapper.BaseMapperEntity;

public class UsersMapper extends BaseMapperEntity<UsersEntity> {

    public static UsersEntity dtoToEntity(UsersPostRequestDTO requestDTO) {
        UsersEntity returnedEnt = new UsersEntity();
        returnedEnt.setId(2);
        return returnedEnt;
    }

    public static UsersGetResponseDTO entityToDto(UsersEntity entity) {
        return new UsersGetResponseDTO(entity.getId(), entity.getCreatedDate(), entity.getUpdatedDate());
    }
}
