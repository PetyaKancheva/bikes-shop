package bg.softuni.bikes_shop.service.impl;

import bg.softuni.bikes_shop.exceptions.CustomObjectNotFoundException;
import bg.softuni.bikes_shop.model.UserRoleEnum;
import bg.softuni.bikes_shop.model.dto.*;
import bg.softuni.bikes_shop.model.entity.UserEntity;
import bg.softuni.bikes_shop.model.entity.UserRoleEntity;
import bg.softuni.bikes_shop.model.events.UserRegistrationEvent;
import bg.softuni.bikes_shop.model.events.UserUpdateProfileEvent;
import bg.softuni.bikes_shop.repository.UserRepository;
import bg.softuni.bikes_shop.repository.UserRoleRepository;
import bg.softuni.bikes_shop.service.UserRoleService;
import bg.softuni.bikes_shop.service.UserService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Locale;
import java.util.Optional;


@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserRoleService userRoleService;
    private final PasswordEncoder passwordEncoder;
    private final ApplicationEventPublisher appEventPublisher;
    private final EmailServiceImpl emailService;

    public UserServiceImpl(UserRepository userRepository, UserRoleService userRoleService, PasswordEncoder passwordEncoder, ApplicationEventPublisher appEventPublisher, EmailServiceImpl emailService, UserRoleRepository userRoleRepository) {
        this.userRepository = userRepository;
        this.userRoleService = userRoleService;
        this.passwordEncoder = passwordEncoder;
        this.appEventPublisher = appEventPublisher;
        this.emailService = emailService;
    }

    @Override
    public void register(UserRegisterDTO userRegisterDTO) {
        UserEntity newUserEntity = mapToEntity(userRegisterDTO);
        newUserEntity.setRoles(List.of(
                userRoleService.getUserRoleByName(UserRoleEnum.USER)
                        .orElseThrow(() -> new CustomObjectNotFoundException("User role USER not found"))));

        userRepository.save(newUserEntity);
        appEventPublisher.publishEvent(new UserRegistrationEvent(
                "UserService", userRegisterDTO.email(), userRegisterDTO.firstName()));
    }

    @Override
    public void updateByUser(UserUpdateDTO userUpdateDTO, String email) {

        UserEntity updatedUser = getExistingUser(email);
        updateMainUserDetails(userUpdateDTO.userMainUpdateDTO(), updatedUser);
        updatedUser.setPassword(passwordEncoder.encode(userUpdateDTO.userSelfUpdateDTO().newPassword()));

        appEventPublisher.publishEvent(
                new UserUpdateProfileEvent("UserService-Update",
                        userUpdateDTO.userMainUpdateDTO().email(),
                        userUpdateDTO.userMainUpdateDTO().firstName(),
                        String.valueOf(Instant.now())));

        userRepository.save(updatedUser);
    }

    @Override
    public void updateByAdmin(AdminUpdateDTO adminUpdateDTO, String email) {

        UserEntity updatedUser = getExistingUser(email);
        updateMainUserDetails(adminUpdateDTO.userMainUpdateDTO(), updatedUser);
        updatedUser.getRoles().clear();
        updatedUser.getRoles().addAll(getRolesFromString(adminUpdateDTO.userAdminUpdateDTO()));

        updatedUser.setPassword(passwordEncoder.encode(adminUpdateDTO.userAdminUpdateDTO().newPassword()));

        appEventPublisher.publishEvent(
                new UserUpdateProfileEvent("UserService-Update",
                        adminUpdateDTO.userMainUpdateDTO().email(),
                        adminUpdateDTO.userMainUpdateDTO().firstName(),
                        String.valueOf(Instant.now())));

        userRepository.save(updatedUser);
    }

    private void updateMainUserDetails(UserMainUpdateDTO userMainUpdateDTO, UserEntity updatedUser) {
        updatedUser.setEmail(userMainUpdateDTO.email());
        updatedUser.setFirstName(userMainUpdateDTO.firstName());
        updatedUser.setLastName(userMainUpdateDTO.lastName());
        updatedUser.setAddress(userMainUpdateDTO.address());
        updatedUser.setCountry(userMainUpdateDTO.country());
    }


    @Override
    @EventListener(UserUpdateProfileEvent.class)
    public void notify(UserUpdateProfileEvent event) {
        emailService.sendProfileUpdateEmail(event.getUserEmail(), event.getUserFirstName(), event.getTimeOfUpdate());
        System.out.println("Notification for profile update is sent to:  " + event.getUserEmail() + " !");
    }

    @Override
    public List<ShortUserDTO> getAllByEmailFirsOrLastName(String searchWord) {
        return userRepository.findAllByEmailFirsOrLastName
                (searchWord).stream().map(UserServiceImpl::mapToShortDTO).toList();
    }

    @Override
    public AdminUpdateDTO getAdminDTO(String email) {

        return new AdminUpdateDTO(getUserMainUpdateDTO(email),
                mapToUserAdminUpdateDTO(getExistingUser(email)));
    }

    @Override
    public UserMainUpdateDTO getUserMainUpdateDTO(String email) {
        return
                mapToMainDTO(getExistingUser(email));
    }


    @Override
    public boolean isUniqueEmail(String value) {
        return userRepository.findUserByEmail(value).isEmpty();
    }

    @Override
    public boolean isPasswordCorrect(String email, String password) {
        return passwordEncoder.matches(password, userRepository
                .findUserByEmail(email).map(UserEntity::getPassword)
                .orElseThrow(() -> new UsernameNotFoundException("User with email: " + email + " not found!")));
    }


    private List<UserRoleEntity> getRolesFromString(UserAdminUpdateDTO userAdminUpdateDTO) {
        return userAdminUpdateDTO
                .roles().stream().map(role -> userRoleService.getUserRoleByName(UserRoleEnum.valueOf(role))
                        .orElseThrow(() -> new CustomObjectNotFoundException("Roles not found"))).toList();
    }

    private UserEntity getExistingUser(String email) {
        return userRepository.findUserByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User with email: " + email + "not found!"));
    }

    private UserEntity mapToEntity(UserRegisterDTO userRegisterDTO) {

        return new UserEntity()
                .setEnabled(false)
                .setFirstName(userRegisterDTO.firstName())
                .setLastName(userRegisterDTO.lastName())
                .setEmail(userRegisterDTO.email())
                .setAddress(userRegisterDTO.address())
                .setCountry(userRegisterDTO.country())
                .setPassword(passwordEncoder.encode(userRegisterDTO.password()));

    }

    private static ShortUserDTO mapToShortDTO(UserEntity u) {
        return new ShortUserDTO(
                u.getEmail(),
                u.getFirstName(),
                u.getLastName());
    }

    private static UserMainUpdateDTO mapToMainDTO(UserEntity u) {
        return new UserMainUpdateDTO(
                u.getEmail(),
                u.getFirstName(),
                u.getLastName(),
                u.getAddress(),
                u.getCountry());

    }

    private static UserAdminUpdateDTO mapToUserAdminUpdateDTO(UserEntity u) {
        return new UserAdminUpdateDTO(
                u.getRoles().stream().map(ur -> ur.getName().name()).toList(),
                "dummy-password",u.getEmail());

    }


}



