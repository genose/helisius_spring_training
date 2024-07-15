package org.genose.helisius_spring_training.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table( name = "user_profile_assets")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileAssetEntity extends BaseCommonEntity {

    @Column(name ="asset_uri" , nullable = false, unique = true, length = 50)
    @Length(min = 12, max = 50)
    private String assetUri;

}
