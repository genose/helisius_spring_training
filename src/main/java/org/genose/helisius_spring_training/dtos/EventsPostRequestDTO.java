package org.genose.helisius_spring_training.dtos;

import java.util.Map;

public class EventsPostRequestDTO extends BasePostRequestDTO {
    public EventsPostRequestDTO(int Id, Map<String, Object> datas) {
        super(Id, datas);
    }
}
