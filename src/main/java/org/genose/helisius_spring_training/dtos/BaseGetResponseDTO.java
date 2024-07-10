package org.genose.helisius_spring_training.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class BaseGetResponseDTO {

    @JsonProperty("id")
    private int id;
    @JsonProperty("date_created")
    private LocalDateTime dateCreated;
    @JsonProperty("date_updated")
    private LocalDateTime dateUpdated;

    public BaseGetResponseDTO(
            int id,
            LocalDateTime dateCreated,
            LocalDateTime dateUpdated

    ) {
        this.id = id;
        this.dateCreated = dateCreated;
        this.dateUpdated = dateUpdated;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (BaseGetResponseDTO) obj;
        return Objects.equals(this.id, that.id) &&
                Objects.equals(this.dateCreated, that.dateCreated) &&
                Objects.equals(this.dateUpdated, that.dateUpdated);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, dateCreated, dateUpdated);
    }

    @Override
    public String toString() {
        return "BaseGetResponseDTO[" +
                "id=" + id + ", " +
                "dateCreated=" + dateCreated + ", " +
                "dateUpdated=" + dateUpdated + ']';
    }


}
