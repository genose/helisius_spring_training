package org.genose.helisius_spring_training.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UsersEntity extends BaseCommonEntity {

    @Column(name = "profile_name", unique = false, nullable = true, length = 20)
    @ColumnDefault("\"New User\"")
    private String profileName;

    @JsonIgnore
    @Column(unique = true, nullable = false, length = 20)
    @Length(min = 5, max = 20)
    private String login;

    @JsonIgnore
    @Column(unique = true, nullable = false, length = 20)
    private String password;

    @UpdateTimestamp
    @Column(name = "last_connection", nullable = false, length = 20)
    private LocalDate lastConnection;

    @JsonIgnore
    private boolean enabled;

    @JsonIgnore
    @Column(unique = true, nullable = true, length = 128)
    @Length(min = 32, max = 128)
    private String email;

    @OneToOne( fetch = FetchType.EAGER )
    @JoinColumn(name = "user_assets_id")
    private UsersProfileAssetsEntity userAssets;

}
