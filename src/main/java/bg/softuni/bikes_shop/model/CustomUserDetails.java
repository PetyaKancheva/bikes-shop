package bg.softuni.bikes_shop.model;

import bg.softuni.bikes_shop.model.entity.UserEntity;
import bg.softuni.bikes_shop.model.entity.UserRoleEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;


public class CustomUserDetails implements UserDetails {
    private String firstName;
    private String lastName;
    private String address;
    private String email;
    private String password;
    private boolean isEnabled;
    private List<GrantedAuthority> authorities;


    public CustomUserDetails(UserEntity userEntity) {
        this.email = userEntity.getEmail();
        this.password = userEntity.getPassword();
        this.isEnabled = userEntity.getEnabled();
        this.authorities = (userEntity.getRoles().stream().map(CustomUserDetails::mapToAuthority).toList());
        this.firstName = userEntity.getFirstName();
        this.lastName = userEntity.getLastName();
        this.address=userEntity.getAddress();
    }

    private static GrantedAuthority mapToAuthority(UserRoleEntity userRoleEntity) {
        return new SimpleGrantedAuthority("ROLE_" + userRoleEntity.getName().name());

    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;   // to customise later
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;   // to customise later
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;   // to customise later
    }

    public String getFirstName() {
        return firstName;
    }


    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }
    public String getAddress() {
        return address;
    }


}

