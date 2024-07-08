package org.genose.helisius_spring_training.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="keywords_category")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class EventsKeywordsCategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "category_name")
    private String categoryName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "associated_category")
    EventsKeywordsEntity referencedKeywordsCategoryId;
}
