package org.genose.helisius_spring_training.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpMethod;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class BasePostRequestDTO extends BaseResponseRequestDTO {
    @JsonIgnore
    @JsonProperty("http_method")
    private String httpMethodDTO = HttpMethod.POST.name();


}
