package org.genose.helisius_spring_training.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;

@Entity
@Table(name = "events_groups_messages")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventsGroupsUsersMessagesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 1000, nullable = false)
    @Length(min = 5, max = 1000)
    private String messageText;

    @Column(length = 256, nullable = true)
    @Length(min = 0, max = 256)
    private String messageTitle;

    @CreationTimestamp
    private LocalDate publishedDate;

    @UpdateTimestamp
    private LocalDate modifiedDate;

    @OneToOne(fetch = FetchType.LAZY)
    private UserEntity referencedUserAuthor;

    @ManyToOne( fetch = FetchType.LAZY)
    @JoinColumn(name = "related_events_groups_id")
    private EventsGroupsUsersEntity relatedEventsGroupsId;


}
