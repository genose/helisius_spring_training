package org.genose.helisius_spring_training.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;
import java.util.Objects;


@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class BasePostRequestDTO extends BaseResponseRequestDTO {
    @JsonProperty("id")
    private int id;

    @JsonProperty("data")
    private Map<String, Object> datas;

    public BasePostRequestDTO(
            int Id,
            Map<String, Object> datas
    ) {
        this.id = Id;
        this.datas = datas;
    }

    public static BasePostRequestDTO createUsersPostRequestDTO() {
        return new BasePostRequestDTO(0, null);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (BasePostRequestDTO) obj;
        return this.id == that.id &&
                Objects.equals(this.datas, that.datas);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, datas);
    }

    @Override
    public String toString() {
        return "BasePostRequestDTO[" +
                "Id=" + id + ", " +
                "datas=" + datas + ']';
    }

}
