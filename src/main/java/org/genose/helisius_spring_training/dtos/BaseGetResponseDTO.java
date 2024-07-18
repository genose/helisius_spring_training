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
public class BaseGetResponseDTO extends BaseResponseRequestDTO {
    @JsonIgnore
    @JsonProperty("http_method")
    private String httpMethodDTO = HttpMethod.GET.name();

    /* public <T extends BaseGetResponseDTO> T assignFromEntity(T fromEntity) {
        this.id = fromEntity.getId();
        GNSJsonUtils.assignFromObject(fromEntity);
        return (T) this;
    }*/
}
