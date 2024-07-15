package org.genose.helisius_spring_training.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
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

@Getter
abstract public class BaseMapperEntity extends ObjectMapper {
    /* ****** ****** ****** ****** */

    /**
     * BaseMapperEntity is an abstract class that provides data conversion methods
     * between DTO (Data Transfer Object) and Entity. It extends the ObjectMapper
     * class provided by the Jackson library to provide JSON processing capabilities.
     * This class encapsulates a logger instance for recording process information.
     *
     * <p>The map methods defined in BaseMapperEntity include:
     * <ul>
     *   <li>convertFromEntityToDTO</li>
     *   <li>convertFromDTOToEntity</li>
     *   <li>convertFromEntityToDTOWithJsonObject</li>
     *   <li>convertFromEntityToDtoWithJsonPath</li>
     * </ul>
     * </p>
     */
    protected Logger logger = LoggerFactory.getLogger(BaseMapperEntity.class);

    public static <S extends BaseGetResponseDTO,
            T extends BaseCommonEntity>
    S convertFromEntityToDTO(T inputEntity,
                             Class<? extends BaseGetResponseDTO> destinationDtoClass) {
        try {
            if (inputEntity == null) {
                throw new IllegalArgumentException("Argument inputEntity is null");
            }

            if (destinationDtoClass == null) {
                throw new IllegalArgumentException("Argument destinationDtoClass is null");
            }

            S returnedDto = (S) destinationDtoClass.getDeclaredConstructor().newInstance();
            int idValueFromEnt = returnedDto.getId();
            int idValueFromDto = inputEntity.getId();

            returnedDto.setId(idValueFromDto);
            GNSClassStackUtils.getEnclosingClass().getSimpleName();

            GNSClassStackUtils.logger
                    .info(GNSClassStackUtils.getEnclosingClass().getSimpleName()
                            + " :: " + GNSClassStackUtils.getEnclosingMethodObject(GNSClassStackUtils.getEnclosingClass().getSimpleName())
                            + " :: " + idValueFromDto + " :: " + idValueFromEnt);

            return returnedDto;
        } catch (Exception e) {
            GNSClassStackUtils.logger
                    .error(GNSClassStackUtils.getEnclosingClass().getSimpleName()
                            + " :: " + GNSClassStackUtils.getEnclosingMethodObject(GNSClassStackUtils.getEnclosingClass().getSimpleName()) + " :: " + e.getMessage());
        }
        return null;
    }

    /**
     * This method is used to convert a DTO object to an Entity.
     *
     * @param inputDto               The DTO object that needs to be converted to an Entity object.
     * @param destinationEntityClass The Class object representing the Entity class.
     * @return Returns the Entity object.
     * @throws IllegalArgumentException if the DTO is not an instance of BasePostRequestDTO
     * @throws Exception                upon encountering errors during the conversion process.
     */
    public static <S extends BaseCommonEntity,
            T extends BasePostRequestDTO>
    S convertFromDTOToEntity(T inputDto,
                             Class<? extends BaseCommonEntity> destinationEntityClass) {
        try {
            if (inputDto == null) {
                throw new IllegalArgumentException("Argument dto is not an instance of BasePostRequestDTO");
            }

            if (destinationEntityClass == null) {
                throw new IllegalArgumentException("Argument argReturnedEntityClass is null");
            }

            S returnedEnt = (S) destinationEntityClass.getDeclaredConstructor().newInstance();
            int idValueFromEnt = returnedEnt.getId();
            int idValueFromDto = inputDto.getId();

            returnedEnt.setId(idValueFromDto);

            GNSClassStackUtils.logger
                    .error(GNSClassStackUtils.getEnclosingClass().getSimpleName()
                            + " :: " + GNSClassStackUtils.getEnclosingMethodObject(GNSClassStackUtils.getEnclosingClass().getSimpleName())
                            + " :: " + idValueFromDto + " :: " + idValueFromEnt);

            return returnedEnt;
        } catch (Exception e) {
            GNSClassStackUtils.logger
                    .error(GNSClassStackUtils.getEnclosingClass().getSimpleName()
                            + " :: " + GNSClassStackUtils.getEnclosingMethodObject(GNSClassStackUtils.getEnclosingClass().getSimpleName())
                            + " :: " + e.getMessage());
        }
        return null;
    }

    /**
     * This method is used to convert an Entity object to a DTO object.
     *
     * @param inputEntity      The Entity object that needs to be converted to a DTO object.
     * @param returnedDtoClass The Class object representing the DTO class.
     * @param argJSONObject    The JSONObject instance containing additional conversion information.
     * @return Returns the DTO instance.
     * @throws IllegalArgumentException if either the Entity object or the DTO class instance is null.
     * @throws Exception                upon encountering errors during the conversion process.
     */
    public <S extends BaseGetResponseDTO,
            T extends BaseCommonEntity,
            U extends JSONObject>

    S convertFromEntityToDTOWithJsonObject(T inputEntity,
                                           Class<? extends BaseGetResponseDTO> returnedDtoClass,
                                           U argJSONObject) {
        try {
            if (inputEntity == null || returnedDtoClass == null) {
                throw new IllegalArgumentException("Entity Object or DTO Class is null");
            }
            S returnedDto = (S) returnedDtoClass.getDeclaredConstructor().newInstance();
            int idValueFromEnt = inputEntity.getId();
            int idValueFromDto = returnedDto.getId();
            // ... returnedDto = GNSJsonUtils.assignObjectFromJsonModel(returnedDto, ent, argJSONObject);

            this.logger.info(this.getClass().getSimpleName()
                    + " :: " + GNSClassStackUtils.getEnclosingMethodObject(this)
                    + " :: " + idValueFromDto + " :: " + idValueFromEnt);
            return returnedDto;
        } catch (Exception e) {
            logger.error(this.getClass().getSimpleName() + " :: " +
                    GNSClassStackUtils.getEnclosingMethodObject(this) + " :: " + e.getMessage());
        }
        return null;
    }


    public <S extends BaseGetResponseDTO, T extends BaseCommonEntity> S
    convertFromEntityToDtoWithJsonPath(T ent,
                                       Class<? extends BaseGetResponseDTO> destinationDtoClass,
                                       String argJsonFile)
            throws IOException, IllegalAccessException, InstantiationException, NoSuchMethodException {
        try {
            if (argJsonFile == null || argJsonFile.isEmpty()) {
                throw new IllegalArgumentException("Argument json file is null or empty");
            }
            File jsonFile = new File(argJsonFile);
            T fromJsonEntity = GNSJsonUtils.assignObjectFromJsonFilePath(argJsonFile, (Class<T>) ent.getClass());
            S returnedDto = (S) destinationDtoClass.getDeclaredConstructor().newInstance();
            returnedDto.setId(fromJsonEntity.getId());
            returnedDto = GNSJsonUtils.assignObjectFromJsonFilePath(argJsonFile, (Class<S>) returnedDto.getClass());
            return returnedDto;
        } catch (Exception e) {
            logger.error(this.getClass().getSimpleName() + " :: " +
                    GNSClassStackUtils.getEnclosingMethodObject(this) + " :: " + e.getMessage());
        }
        return null;
    }
}

