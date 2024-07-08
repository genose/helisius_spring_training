package org.genose.helisius_spring_training.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.validator.constraints.Length;

import java.util.ArrayList;
import java.util.Collection;

@Entity
@Table(name = "events_groups_users")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class EventsGroupsUsersEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="group_name", nullable = false, unique = true, length = 50)
    @ColumnDefault("\"Nouveau Groupe\"")
    @Length(min = 5, max = 50)
    private String groupName;

    @OneToOne
    private UserEntity referencedUserAuthor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "related_events_id")
    private EventsEntity relatedEventsId;

    @OneToMany(mappedBy = "relatedEventsGroupsId", orphanRemoval = true )
    private Collection<EventsGroupsUsersMessagesEntity> referencedGroupsMessagesId;

}
