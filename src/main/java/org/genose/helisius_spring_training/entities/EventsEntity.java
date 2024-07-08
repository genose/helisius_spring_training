package org.genose.helisius_spring_training.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.util.Collection;

@Entity
@Table(name = "events")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class EventsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

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

    @OneToOne
    @JoinColumn(name = "referenced_events_address_id")
    private AddressEventsEntity referencedAddressEventsID;

    @OneToMany(mappedBy = "relatedEventsId", fetch = FetchType.LAZY)
    private Collection<EventsMediasEntity> referencedEventsMediaID;

    @OneToMany(mappedBy = "relatedEventsId", fetch = FetchType.LAZY)
    private Collection<EventsGroupsUsersEntity> referencedEventGroupsID;

    @ManyToMany()
    @JoinTable(name = "fk_events_referer_keywords",
            joinColumns = @JoinColumn(name = "events_id"),
            inverseJoinColumns = @JoinColumn(name = "keywords_id")

    )
    private Collection<EventsKeywordsEntity> referencedKeywordsList;
}
