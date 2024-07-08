package org.genose.helisius_spring_training.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Entity
@Table( name = "user_profile_assets")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserProfileAssetsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name ="asset_uri" , nullable = false, unique = true, length = 50)
    @Length(min = 12, max = 50)
    private String assetUri;

}
