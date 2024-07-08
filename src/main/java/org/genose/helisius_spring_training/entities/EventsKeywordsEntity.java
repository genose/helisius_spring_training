package org.genose.helisius_spring_training.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.util.List;

@Entity
@Table(name = "events_keywords")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class EventsKeywordsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    @ColumnDefault("\"New Keyword\"")
    private String keyword;

    @Column(nullable = true)
    @ColumnDefault("\"New Keyword description\"")
    private String description;

    @OneToMany(fetch=FetchType.LAZY, mappedBy = "referencedKeywordsCategoryId")
    List<EventsKeywordsCategoryEntity> referencedCategoryId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_keywords")
    private EventsEntity referencedEventId;

}
