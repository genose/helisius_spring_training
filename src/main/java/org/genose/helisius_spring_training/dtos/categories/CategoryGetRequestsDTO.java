package org.genose.helisius_spring_training.dtos.categories;

import org.genose.helisius_spring_training.dtos.BaseGetResponseDTO;
import org.genose.helisius_spring_training.entities.EventKeywordEntity;

import java.util.List;

public class CategoryGetRequestsDTO extends BaseGetResponseDTO {
    private int id;
    private String name;
    private String img;
    private List<EventKeywordEntity> keywords;
}
