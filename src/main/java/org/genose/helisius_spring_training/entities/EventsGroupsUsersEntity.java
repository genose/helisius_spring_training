package org.genose.helisius_spring_training.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.validator.constraints.Length;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users_events_groups")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class EventsGroupsUsersEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, unique = true, length = 50)
    @ColumnDefault("\"Nouveau Groupe\"")
    @Length(min = 5, max = 50)
    private String groupName;

    @OneToMany(mappedBy = "referencedEventGroupId")
    private Collection<EventsGroupsUsersMessagesEntity> referencedMessagesId = new ArrayList<>();

}
