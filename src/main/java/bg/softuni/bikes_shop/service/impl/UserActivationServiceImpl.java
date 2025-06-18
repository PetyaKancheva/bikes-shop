package bg.softuni.bikes_shop.service.impl;

import bg.softuni.bikes_shop.exceptions.CustomObjectNotFoundException;
import bg.softuni.bikes_shop.model.entity.UserActivationCodeEntity;
import bg.softuni.bikes_shop.model.entity.UserEntity;
import bg.softuni.bikes_shop.model.events.UserRegistrationEvent;
import bg.softuni.bikes_shop.repository.ActivationCodeRepository;
import bg.softuni.bikes_shop.repository.UserRepository;
import bg.softuni.bikes_shop.service.EmailService;
import bg.softuni.bikes_shop.service.UserActivationService;
import jakarta.transaction.Transactional;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.Instant;
import java.util.Optional;
import java.util.Random;

import static java.time.temporal.ChronoUnit.DAYS;

@Service
public class UserActivationServiceImpl implements UserActivationService {
    private static final int ACTIVATION_CODE_LENGTH = 15;
    private static final String ACTIVATION_CODE_SYMBOLS = "abcdefghijklmnopqrstuvwxyzABCDEFGJKLMNPRSTUVWXYZ0123456789";
    private final EmailService emailService;
    private final UserRepository userRepository;
    private final ActivationCodeRepository activationCodeRepository;

    public UserActivationServiceImpl(EmailService emailService, UserRepository userRepository, ActivationCodeRepository activationCodeRepository) {
        this.emailService = emailService;
        this.userRepository = userRepository;
        this.activationCodeRepository = activationCodeRepository;

    }

    @Override
    @EventListener(UserRegistrationEvent.class)
    public void userRegistered(UserRegistrationEvent event) {

        emailService.sendRegistrationEmail(event.getUserEmail(), event.getUserFirstName(),
                createActivationCode(event.getUserEmail()));

        System.out.println("User with name: " + event.getUserFirstName() + " registered");
    }

    @Override
    @Transactional
    public void cleanUpObsoleteActivationLinks() {
        activationCodeRepository.deleteUserActivationCodeEntitiesByCreatedIsBefore(Instant.now() .minus(1, DAYS));
    }

    @Override
    public boolean userActivate(String userActivationCode) {
        if (activationCodeRepository.findByActivationCode(userActivationCode).isPresent()) {
            Optional<UserActivationCodeEntity> uaEntity = activationCodeRepository.findByActivationCode(userActivationCode);
            if (DAYS.between(uaEntity.get().getCreated(), Instant.now()) <= 1) {
            if(userRepository.findById(uaEntity.get().getUser().getId()).isPresent()) {

                userRepository.save(userRepository.findById(uaEntity.get().getUser().getId()).get().setEnabled(true));

                activationCodeRepository.delete(uaEntity.orElseThrow());
                return true;
            }

            }
        }
        return false;
    }

    @Override
    public String createActivationCode(String userEmail) {

        String activationCode = generateActivationCode();

        UserActivationCodeEntity userActivationCodeEntity = new UserActivationCodeEntity();
        userActivationCodeEntity.setActivationCode(activationCode);
        userActivationCodeEntity.setCreated(Instant.now());
        userActivationCodeEntity.setUser(userRepository.findUserByEmail(userEmail).orElseThrow(() -> new CustomObjectNotFoundException("User not found")));

        activationCodeRepository.save(userActivationCodeEntity);

        return activationCode;
    }

    private String generateActivationCode() {

        StringBuilder activationCode = new StringBuilder();
        Random random = new SecureRandom();

        for (int i = 0; i < ACTIVATION_CODE_LENGTH; i++) {
            int randInx = random.nextInt(ACTIVATION_CODE_SYMBOLS.length());
            activationCode.append(ACTIVATION_CODE_SYMBOLS.charAt(randInx));
        }

        return activationCode.toString();
    }
}
