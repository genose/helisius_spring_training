package org.genose.helisius_spring_training.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.validator.constraints.Length;

import java.util.Collection;

@Entity
@Table(name = "events_groups_users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
// @DynamicUpdate
public class EventGroupUserEntity extends BaseCommonEntity {

    @Column(name = "group_name", nullable = false, unique = true, length = 50)
    @ColumnDefault("\"Nouveau Groupe\"")
    @Length(min = 5, max = 50)
    private String groupName;

    @OneToOne
    // @JsonProperty(value = "user_author_id")
    private UserEntity referencedUserAuthorId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "related_events_id")
    private EventEntity relatedEventsId;

    // Apply map / filter in DTO to Obtain Users List
    @OneToMany(mappedBy = "relatedEventsGroupsId", orphanRemoval = true)
    private Collection<EventGroupUserMessageEntity> referencedGroupsMessagesId;

}
