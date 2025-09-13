package bg.softuni.bikes_shop.util;

import bg.softuni.bikes_shop.model.CustomUserDetails;
import bg.softuni.bikes_shop.model.UserRoleEnum;
import bg.softuni.bikes_shop.model.entity.OrderEntity;
import bg.softuni.bikes_shop.model.entity.UserEntity;
import bg.softuni.bikes_shop.repository.UserRepository;
import bg.softuni.bikes_shop.repository.UserRoleRepository;
import com.fasterxml.jackson.core.Base64Variant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Component
public class TestUserUtil {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;
    @Autowired
    private final PasswordEncoder passwordEncoder;

    public TestUserUtil(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }


    public CustomUserDetails createTestUser(String email) {
        return new CustomUserDetails(createUser(email, List.of(UserRoleEnum.USER)));
    }

    public CustomUserDetails createTestAdmin(String email) {
        return new CustomUserDetails(createUser(email, List.of(UserRoleEnum.ADMIN)));
    }

    public CustomUserDetails createTestEmployee(String email) {
        return new CustomUserDetails(createUser(email, List.of(UserRoleEnum.EMPLOYEE)));
    }

    private UserEntity createUser(String email, List<UserRoleEnum> roles) {

        var roleEntities = userRoleRepository.findAllByNameIn(roles);

        UserEntity newUser = new UserEntity()
                .setEnabled(false)
                .setFirstName("First name test")
                .setLastName("Last name test")
                .setEmail(email)
                .setAddress("Address Test")
                .setCountry("Country Test")
                .setRoles(roleEntities)
                .setPassword(passwordEncoder.encode("test1234"));
        return userRepository.save(newUser);
    }

    public void cleanUp() {
        userRepository.deleteAll();
    }

}
