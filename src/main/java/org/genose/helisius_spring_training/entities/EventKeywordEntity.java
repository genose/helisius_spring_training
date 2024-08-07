package org.genose.helisius_spring_training.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.util.Collection;

@Entity
@Table(name = "events_keywords")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class EventKeywordEntity extends BaseCommonEntity {

    @Column(nullable = false)
    @ColumnDefault("\"New Keyword\"")
    private String keyword;

    @Column(nullable = true)
    @ColumnDefault("\"New Keyword description\"")
    private String description;

    @ManyToMany()
    @JoinTable(name = "fk_keywords_referer_category",
            joinColumns = @JoinColumn(name = "keywords_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")

    )
    private Collection<EventCategoryKeywordEntity> referencedCategoryId;
}
