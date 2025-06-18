package bg.softuni.bikes_shop.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;
@Entity
@Table(name="user_activation_codes")
public class UserActivationCodeEntity extends BaseEntity{

    @Column(name="activation_code",nullable = false)
    private  String activationCode;
    @Column(name="created")
    private Instant created;
    @ManyToOne
    @JoinColumn(name="user_id",nullable=false)
    private UserEntity user;

    public String getActivationCode() {
        return activationCode;
    }

    public UserActivationCodeEntity setActivationCode(String activationCode) {
        this.activationCode = activationCode;
        return this;
    }

    public Instant getCreated() {
        return created;
    }

    public UserActivationCodeEntity setCreated(Instant created) {
        this.created = created;
        return this;
    }

    public UserEntity getUser() {
        return user;
    }

    public UserActivationCodeEntity setUser(UserEntity user) {
        this.user = user;
        return this;
    }
}
