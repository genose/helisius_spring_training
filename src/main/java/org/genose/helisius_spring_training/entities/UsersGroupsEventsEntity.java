package org.genose.helisius_spring_training.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.validator.constraints.Length;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Table(name="users_groups_events")
public class UsersGroupsEventsEntity  {

    @EmbeddedId
    private EventsGroupsUsersMessagesIDEntity idGroup;

    @Column(name = "events_id")
    private EventsEntity referencedEventForGroupsId;

    @Column(name = "events_group_messages")
    private EventsGroupsUsersMessagesEntity referencedMessagesForGroupsId;

    @Column(name="group_name",length = 50, nullable = false)
    @ColumnDefault("\"Nouveau Groupe\"")
    @Length(min=5, max =50)
    private String groupName;

    @Column(name = "user_organisator_id")
    private UserEntity usersOrganisatorId;

    @OneToMany(mappedBy = "referencedEventGroupId")
    private Collection<EventsGroupsUsersMessagesEntity> eventsGroupsMessagesList = new ArrayList<>();

}
