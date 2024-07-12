package org.genose.helisius_spring_training.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.genose.helisius_spring_training.dtos.BaseGetResponseDTO;
import org.genose.helisius_spring_training.dtos.BasePostRequestDTO;
import org.genose.helisius_spring_training.entities.BaseCommonEntity;
import org.genose.helisius_spring_training.utils.GNSClassStackUtils;
import org.genose.helisius_spring_training.utils.GNSJsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.configurationprocessor.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.function.Function;

abstract public class BaseMapperEntity extends ObjectMapper {
    /* ****** ****** ****** ****** */
    protected Logger logger = LoggerFactory.getLogger(BaseMapperEntity.class);

    /* ****** ****** ****** ****** */
    public <S extends BaseCommonEntity, T extends BasePostRequestDTO, U extends JSONObject> S dtoToEntity(T dto, Function<Integer, S> factory) {
        S returnedEnt = factory.apply(dto.getId());
        int idValueFromEnt = returnedEnt.getId();
        int idValueFromDto = dto.getId();

        returnedEnt.setId(idValueFromDto);

        this.logger.info(this.getClass().getSimpleName()
                + " :: " + GNSClassStackUtils.getEnclosingMethodObject(this)
                + " :: " + idValueFromDto + " :: " + idValueFromEnt);

        return returnedEnt;
    }

    /* ****** ****** ****** ****** */
    public <S extends BaseGetResponseDTO, T extends BaseCommonEntity, U extends JSONObject> S entityToDto(T ent, Function<Integer, S> factory) {
        S returnedDto = factory.apply(ent.getId());
        int idValueFromEnt = ent.getId();
        int idValueFromDto = returnedDto.getId();

        returnedDto = GNSJsonUtils.assignObjectFromJson(returnedDto, ent.getClass());

        this.logger.info(this.getClass().getSimpleName()
                + " :: " + GNSClassStackUtils.getEnclosingMethodObject(this)
                + " :: " + idValueFromDto + " :: " + idValueFromEnt);
        return returnedDto;

    }

    public <S extends BaseGetResponseDTO, T extends BaseCommonEntity> S entityToDto(T ent, Function<Integer, S> factory, String argJsonFile) {

        try {
            T fromJsonEntity = this.readValue(new File(argJsonFile), (T).getClass());
            S returnedDto = factory.apply(ent.getId());
            returnedDto.setId(fromJsonEntity.getId());

            // returnedDto.assignFromEntity(fromJsonEntity);
            returnedDto = (S) GNSJsonUtils.assignObjectFromJsonFilePath(argJsonFile, returnedDto.getClass());
            return returnedDto;
        } catch (IOException e) {
            logger.error(this.getClass().getSimpleName() + " :: " +
                    GNSClassStackUtils.getEnclosingMethodObject(this) + " :: " + argJsonFile + " :: " + e.getMessage());
        }
        return null;
    }
}

