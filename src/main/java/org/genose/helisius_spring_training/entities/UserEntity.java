package org.genose.helisius_spring_training.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.genose.helisius_spring_training.enums.UserRoleEnum;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@DynamicUpdate
public class UserEntity extends BaseCommonEntity implements UserDetails {

    @Enumerated(EnumType.STRING)
    // @Column(name = "role", nullable = false)
    private UserRoleEnum userRole;

    @Column(name = "profile_nickname", unique = true, nullable = false, length = 32)
    @ColumnDefault("\"New User nickname\"")
    @Length(min = 5, max = 32)
    private String profileNickname;
    @Column(name = "profile_description", unique = true, nullable = false, length = 320)
    @ColumnDefault("\"New User profiile description\"")
    @Length(min = 5, max = 320)
    private String profileDescription;
    @Column(name = "first_name", unique = false, nullable = false, length = 55)
    @ColumnDefault("\"New User firstname\"")
    @Length(min = 5, max = 55)
    private String firstName;
    @Column(name = "last_name", unique = false, nullable = false, length = 55)
    @ColumnDefault("\"New User lastname\"")
    @Length(min = 5, max = 55)
    private String lastName;

    @JsonIgnore
    @Column(unique = true, nullable = false, length = 20)
    @Length(min = 5, max = 20)
    @ColumnDefault("\"New User Login\"")
    private String username;

    @JsonIgnore
    @Column(unique = true, nullable = false, length = 245)
    // @ColumnDefault("SHA2(\"new_password\", 512)")
    @Length(min = 12, max = 245)
    private String password;
    // @CreationTimestamp
    @UpdateTimestamp
    @Column(name = "last_connection", nullable = false, length = 20)
    private LocalDate lastConnection;

    @JsonIgnore
    @Column(name = "user_enabled", nullable = false)
    @ColumnDefault(value = "false")
    private boolean userEnabled;

    @JsonIgnore
    @Column(unique = true, nullable = false, length = 128)
    @Length(min = 32, max = 128)
    private String email;

    /* ****** ****** ****** ****** */
    // @OneToOne(fetch = FetchType.EAGER)
    // @JoinColumn(name = "user_assets_id")
    // @JsonProperty("avatar")
    // private UserProfileAssetEntity userAssets;
    /* ****** ****** ****** ****** */
    @Column(nullable = false, length = 255)
    @ColumnDefault(value = "\"/assets/images/avatars/avatar1.svg\"")
    private String avatar;
    /* ****** ****** ****** ****** */

    /* ****** ****** ****** ****** */
    // @JsonSerialize
    // private Serializable encodedToken;
    /* ****** ****** ****** ****** */

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + this.userRole));
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    public boolean isEnabled() {
        return userEnabled;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;// UserDetails.super.isAccountNonLocked();
    }

    public boolean isAccountNonExpired() {
        return true;
    }

    /* ****** ****** ****** ****** */
    //  public void setEncodedToken(String localEncodedToken) {
    //     this.encodedToken = localEncodedToken;
    // }
    /* ****** ****** ****** ****** */

}
