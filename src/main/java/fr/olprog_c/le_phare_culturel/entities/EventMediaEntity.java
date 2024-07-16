package fr.olprog_c.le_phare_culturel.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "events_medias")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventMediaEntity extends BaseCommonEntity {

  @Override
  public int hashCode() {
    return super.hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (!super.equals(obj))
      return false;
    if (getClass() != obj.getClass())
      return false;
    return true;
  }

  @Column(name = "media_url", nullable = false, length = 255)
  @Length(min = 12, max = 255)
  private String mediaUrl;

  @ColumnDefault("0")
  @Column(name = "media_order", nullable = false)
  @Min(0)
  @Max(12)
  private int mediaOrder;

  @ManyToOne
  @JoinColumn(name = "related_events_id", nullable = false)
  private EventEntity relatedEventsId;
}
