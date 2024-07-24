package fr.olprog_c.le_phare_culturel.entities;

import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "events_groups_users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
// @DynamicUpdate
public class EventGroupUserEntity extends BaseCommonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "group_name", nullable = false, length = 50)
    private String groupName;

    @Column(name = "group_size", nullable = false, columnDefinition = "TINYINT UNSIGNED")
    private Integer groupMaxSize;

    @Temporal(TemporalType.TIMESTAMP)
    private String timeMeet;

    @Column(name = "description", nullable = false, length = 255)
    private String description;

    @ManyToOne(optional = false)
    private UserEntity referencedUserAuthor;

    @ManyToOne()
    @JoinColumn(name = "related_events")
    private EventEntity relatedEvents;

    @ManyToMany(mappedBy = "referencedGroups")
    private Collection<UserEntity> referencedUserList;

    @OneToMany(mappedBy = "relatedEventsGroups", orphanRemoval = true)
    @JsonBackReference
    private Collection<EventGroupUserMessageEntity> referencedGroupsMessages;

}
