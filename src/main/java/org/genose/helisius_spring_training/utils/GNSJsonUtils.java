package org.genose.helisius_spring_training.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.genose.helisius_spring_training.entities.BaseCommonEntity;

import java.io.File;

public class GNSJsonUtils {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static <T> T assignFromJsonString(String json, Class<T> valueType) {
        try {
            return objectMapper.readValue(json, valueType);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T assignObjectFromJsonFilePath(String argJsonFileFilePath, Class<T> valueType) {
        try {
            return objectMapper.readValue(new File(argJsonFileFilePath), valueType);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <S> S assignObjectFromJson(S returnedDto, Class<? extends BaseCommonEntity> aClass) {
        try {
            String json = objectMapper.writeValueAsString(returnedDto);
            return (S) objectMapper.readValue(json, aClass);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T assignFromObject(T fromEntity) {
        return null;
    }

    /* public static S assignObjectFromJsonModel(S returnedDto,
                                              T ent,
                                              U argJSONObject) {
        try {
            String json = objectMapper.writeValueAsString(returnedDto);
            return (S) objectMapper.readValue(json, ent.getClass());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }*/
}