package org.genose.helisius_spring_training.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.genose.helisius_spring_training.dtos.BasePostRequestDTO;
import org.genose.helisius_spring_training.entities.BaseCommonEntity;
import org.genose.helisius_spring_training.utils.ClassStackUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BaseMapperEntity extends ObjectMapper {
    protected Logger logger = LoggerFactory.getLogger(BaseMapperEntity.class);


    public BaseCommonEntity dtoToEntity(BasePostRequestDTO dto) {
        BaseCommonEntity returnedEnt = new BaseCommonEntity();
        Integer idValue = returnedEnt.getId();
        this.logger.info(this.getClass().getSimpleName() + " :: " + ClassStackUtils.getEnclosingMethodObject(this));

        return returnedEnt;
    }

    /* ****** ****** ****** ****** */
    /*public static BaseGetResponseDTO entityToDto(BaseCommonEntity s) {
        return new BaseGetResponseDTO(s.getId(), null, null);
    }*/

    /*public <S extends BaseGetResponseDTO, T extends BaseCommonEntity> S entityToDto(T ent) {
        S s1 = new S();
        return s1;
    }*/
}
