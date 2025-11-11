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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;


@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserRoleService userRoleService;
    private final PasswordEncoder passwordEncoder;
    private final ApplicationEventPublisher appEventPublisher;
    private final EmailServiceImpl emailService;
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

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
    public void updateByAdmin(UserUpdateByAdminDTO userUpdateByAdminDTO) {
        UserEntity updatedUser = getExistingUser(userUpdateByAdminDTO.oldEmail());

        setDetailsForAdminUpdate(userUpdateByAdminDTO, updatedUser);

        publishEvent( userUpdateByAdminDTO.newEmail(), userUpdateByAdminDTO.firstName());

        userRepository.save(updatedUser);
    }


    @Override
    @EventListener(UserUpdateProfileEvent.class)
    public void sendEmail(UserUpdateProfileEvent event) {
        emailService.sendProfileUpdateEmail(event.getUserEmail(), event.getUserFirstName(), event.getTimeOfUpdate());
        logger.info("Notification for profile update is sent to:  {} !", event.getUserEmail());
    }

    @Override
    public List<ShortUserProfileDTO> getAllByEmailFirsOrLastName(String searchWord) {
        return userRepository.findAllByEmailFirsOrLastName
                (searchWord).stream().map(UserServiceImpl::mapToShortDTO).toList();
    }

    @Override
    public UserUpdateByAdminDTO getUserAdminUpdateDTO(String email) {
        return mapToUserUpdateByAdminDTO(getExistingUser(email));
    }

    @Override
    public UserUpdateMainDetailsDTO getUserMainUpdateDTO(String email) {
        return mapToUserUpdateMainDetailsDTO(getExistingUser(email));
    }

    @Override
    public void updateMainUserDetails(UserUpdateMainDetailsDTO dto) {
        UserEntity userToUpdate = getExistingUser(dto.currentEmail());

        setDetailsForUserUpdate(dto, userToUpdate);

        publishEvent(dto.currentEmail(), dto.firstName());

        userRepository.save(userToUpdate);
    }

    @Override
    public void updateEmail(UserUpdateEmailDTO dto) {
        UserEntity userToUpdate = getExistingUser(dto.oldEmail());
        // check password is matching??
        userToUpdate.setEmail(dto.newEmail());
        publishEvent(dto.newEmail(),userToUpdate.getFirstName());
        userRepository.save(userToUpdate);
    }

    @Override
    public void updatePassword(UserUpdatePasswordDTO dto) {
        UserEntity userToUpdate = getExistingUser(dto.currentEmail());
        // check password is matching??
        userToUpdate.setPassword(passwordEncoder.encode(dto.newPassword()));

        publishEvent(dto.currentEmail(),userToUpdate.getFirstName());
        userRepository.save(userToUpdate);

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

    private void setDetailsForUserUpdate(UserUpdateMainDetailsDTO dto, UserEntity userToUpdate) {
        userToUpdate.setFirstName(dto.firstName());
        userToUpdate.setLastName(dto.lastName());
        userToUpdate.setAddress(dto.address());
        userToUpdate.setCountry(dto.country());
    }

    private void setDetailsForAdminUpdate(UserUpdateByAdminDTO dto, UserEntity updatedUser) {
        updatedUser.setFirstName(dto.firstName());
        updatedUser.setLastName(dto.lastName());
        updatedUser.setAddress(dto.address());
        updatedUser.setCountry(dto.country());

        updatedUser.setEmail(dto.newEmail());
        updatedUser.getRoles().clear();
        updatedUser.getRoles().addAll(getRolesFromString(dto));

        updatedUser.setPassword(passwordEncoder.encode(dto.newPassword()));
    }

    private List<UserRoleEntity> getRolesFromString(UserUpdateByAdminDTO userUpdateByAdminDTO) {
        return userUpdateByAdminDTO
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

    private static ShortUserProfileDTO mapToShortDTO(UserEntity u) {
        return new ShortUserProfileDTO(
                u.getEmail(),
                u.getFirstName(),
                u.getLastName());
    }

    private static UserUpdateMainDetailsDTO mapToUserUpdateMainDetailsDTO(UserEntity u) {
        return new UserUpdateMainDetailsDTO(
                u.getEmail(),
                u.getFirstName(),
                u.getLastName(),
                u.getAddress(),
                u.getCountry());

    }

    private static UserUpdateByAdminDTO mapToUserUpdateByAdminDTO(UserEntity u) {
        return new UserUpdateByAdminDTO(
                u.getEmail(),
                u.getEmail(),
                u.getRoles().stream().map(ur -> ur.getName().name()).toList(),
                "newPassword",
                u.getFirstName(),
                u.getLastName(),
                u.getAddress(),
                u.getCountry());

    }

    private void publishEvent(String email, String firstName) {
        appEventPublisher.publishEvent(
                new UserUpdateProfileEvent("UserService-Update",
                        email,
                        firstName,
                        String.valueOf(Instant.now())));
    }

}



