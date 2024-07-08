package org.genose.helisius_spring_training.entities;

import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class EventsGroupsUsersMessagesIDEntity implements Serializable {

    private int eventId;
    private int groupId;
    private int userId;

}
