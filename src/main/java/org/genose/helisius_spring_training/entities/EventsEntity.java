package org.genose.helisius_spring_training.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="events")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class EventsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  int id;

    @Column(nullable = false, length = 50)
    @ColumnDefault("\"Nouveau Titre\"")
    private String titre;

    @Column(nullable = false, length = 1000)
    @ColumnDefault("\"Nouvel Evenement\"")
    private String description;

    @CreationTimestamp
    private LocalDate dateDebut;

    @CreationTimestamp
    private LocalDate dateFin;

    @Column(nullable = false)
    private boolean isActive;

    @ManyToOne
    @MapsId("referencedEventForGroupsId")
    private EventsGroupsUsersEntity eventId;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "referencedEventId")
    private List<EventsKeywordsEntity> keywordsList = new ArrayList<>();
}
