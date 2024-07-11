package org.genose.helisius_spring_training.entities;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "events_groups_messages")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@DynamicUpdate
@DynamicInsert
public class EventsGroupsUsersMessagesEntity extends BaseCommonEntity {

    @Column(length = 1000, nullable = false)
    @Length(min = 5, max = 1000)
    @ColumnDefault("\"Body Nouveau Message\"")
    private String messageText;

    @Column(length = 256, nullable = true)
    @Length(min = 5, max = 256)
    @ColumnDefault("\"Nouveau Message\"")
    private String messageTitle;

    @OneToOne(fetch = FetchType.LAZY)
    // @JsonProperty(value = "user_author_id")
    private UsersEntity referencedUserAuthorId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "related_events_groups_id")
    private EventsGroupsUsersEntity relatedEventsGroupsId;

}
