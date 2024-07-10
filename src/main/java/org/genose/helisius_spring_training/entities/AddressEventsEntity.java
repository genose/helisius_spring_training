package org.genose.helisius_spring_training.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "adress_events")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressEventsEntity extends BaseCommonEntity {

    private String name;
    @Length(min = 1, max = 128)
    private String address;

    @Column(length = 72, nullable = false)
    @ColumnDefault("\"Lille\"")
    @Length(min =2, max = 12)
    private String city;

    @Column(length = 12, nullable = true)
    @ColumnDefault("\"59000\"")
    @Length(min =5, max = 12)
    private String postalCode;

    @Column(length = 20, nullable = true)
    @Length(min =0, max = 20)
    private String phone;

    @Column(length = 128, nullable = true)
    @ColumnDefault("\"contact@genose.org\"")
    @Length(min =0, max = 128)
    private String email;

    @Column(length = 1000, nullable = true)
    @ColumnDefault("\"Commentaire ... \"")
    @Length(min =0, max = 1000)
    private String comment;

    // ..... geolocatisation
    @Column(length = 20, nullable = false)
    @ColumnDefault("50.633333")
    @Length(min =0, max = 20)
    private double latitude;

    @Column(length = 20, nullable = false)
    @ColumnDefault("3.066667")
    @Length(min =5, max = 12)
    private double longitude;
}
