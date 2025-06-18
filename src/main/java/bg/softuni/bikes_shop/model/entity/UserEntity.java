package bg.softuni.bikes_shop.model.entity;



import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="users")
public class UserEntity extends BaseEntity{

    @Size(min = 3, max=15, message= "Must be between 3 and 15 characters.")
    @Column(name="first_name", nullable = false)
    private String firstName;
    @Size(min = 3, max=15, message= "Must be between 3 and 15 characters.")
    @Column(name="last_name", nullable = false)
    private String lastName;
    @Size(min=3, message="Must be at least 3 characters.")
    @Column(name="address", nullable = false)
    private String address;
    @Column(name="country")
    private String country;
    @Column(name="password", nullable = false)
    private String password;
    @Email
    @Column(name="e_mail",nullable = false)
    private String email;
    @OneToMany(mappedBy = "buyer")
    private List<OrderEntity> orders;
    @Column(name="is_enabled")
    private Boolean isEnabled;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<UserRoleEntity>roles = new ArrayList<>();;

    public String getFirstName() {
        return firstName;
    }

    public UserEntity setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public UserEntity setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public UserEntity setAddress(String address) {
        this.address = address;
        return this;
    }

    public String getCountry() {
        return country;
    }

    public UserEntity setCountry(String country) {
        this.country = country;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserEntity setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserEntity setEmail(String email) {
        this.email = email;
        return this;
    }

    public List<OrderEntity> getOrders() {
        return orders;
    }

    public UserEntity setOrders(List<OrderEntity> orders) {
        this.orders = orders;
        return this;
    }


    public Boolean getEnabled() {
        return isEnabled;
    }

    public UserEntity setEnabled(Boolean enabled) {
        isEnabled = enabled;
        return this;
    }

    public List<UserRoleEntity> getRoles() {
        return roles;
    }

    public UserEntity setRoles(List<UserRoleEntity> roles) {
        this.roles = roles;
        return this;
    }
}
