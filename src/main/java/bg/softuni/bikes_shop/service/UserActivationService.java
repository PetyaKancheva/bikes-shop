package bg.softuni.bikes_shop.service;

import bg.softuni.bikes_shop.model.events.UserRegistrationEvent;

public interface UserActivationService {

    void userRegistered(UserRegistrationEvent event);

    void cleanUpObsoleteActivationLinks();

    boolean userActivate(String userActivationCode);

    String createActivationCode(String userEmail);

}
