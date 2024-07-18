package org.genose.helisius_spring_training.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;


@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class BaseResponseRequestDTO {
    @JsonIgnore
    public int statusCodeDTO = HttpStatus.ALREADY_REPORTED.value();

    public int getId() {
        return (-1);
    }

    public void setId(int value) {
        // .....
    }

}
