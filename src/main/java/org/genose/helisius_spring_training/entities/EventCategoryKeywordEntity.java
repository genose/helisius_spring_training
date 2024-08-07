package org.genose.helisius_spring_training.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Entity
@Table(name = "events_keywords_category")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class EventCategoryKeywordEntity extends BaseCommonEntity {

    @Column(name = "category_name")
    private String categoryName;

    @ManyToMany(mappedBy = "referencedCategoryId", fetch = FetchType.LAZY)
    private Collection<EventKeywordEntity> referencedKeywordsCategoryId;
}
