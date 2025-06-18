package bg.softuni.bikes_shop.service;

import java.time.LocalDateTime;

public interface EmailService {
    void sendRegistrationEmail(String userEmail, String userFirstName, String activationCode);
    void sendProfileUpdateEmail(String userEmail, String userFirstName , String time);


}
