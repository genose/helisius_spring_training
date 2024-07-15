package org.genose.helisius_spring_training.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class BaseResponseRequestDTO {
    @JsonIgnore
    public int statusCodeDTO;

    public int getId() {
        return (-1);
    }

    public void setId(int value) {
        // .....
    }

}
