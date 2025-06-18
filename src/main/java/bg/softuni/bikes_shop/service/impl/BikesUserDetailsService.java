package bg.softuni.bikes_shop.service.impl;

import bg.softuni.bikes_shop.model.CustomUserDetails;
import bg.softuni.bikes_shop.model.entity.UserEntity;
import bg.softuni.bikes_shop.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class BikesUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public BikesUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findUserByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User with email: " + email + " not found!"));

        return new CustomUserDetails(userEntity);
    }

}
