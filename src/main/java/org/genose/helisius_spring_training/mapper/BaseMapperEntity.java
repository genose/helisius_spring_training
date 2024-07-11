package org.genose.helisius_spring_training.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.genose.helisius_spring_training.dtos.BaseGetResponseDTO;
import org.genose.helisius_spring_training.dtos.BasePostRequestDTO;
import org.genose.helisius_spring_training.entities.BaseCommonEntity;

public class BaseMapperEntity  extends ObjectMapper {

    public static BaseCommonEntity dtoToEntity(BasePostRequestDTO dto) {
        BaseCommonEntity returnedEnt = new BaseCommonEntity();
        returnedEnt.getId();

        return returnedEnt;
    }

    /* ****** ****** ****** ****** */
    /*public static BaseGetResponseDTO entityToDto(BaseCommonEntity s) {
        return new BaseGetResponseDTO(s.getId(), null, null);
    }*/

   /*  public static <S> Object entityToDto(S s) {
        S s1 = new S();
        return s1;
    }*/
}
