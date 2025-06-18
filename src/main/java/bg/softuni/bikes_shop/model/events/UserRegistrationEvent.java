package bg.softuni.bikes_shop.model.events;

import org.springframework.context.ApplicationEvent;

import java.time.Clock;

public class UserRegistrationEvent extends ApplicationEvent {
    private final String userEmail;
    private final String userFirstName;

    public UserRegistrationEvent(Object source, String userEmail, String userFirstName) {
        super(source);
        this.userEmail = userEmail;
        this.userFirstName = userFirstName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getUserFirstName() {
        return userFirstName;
    }
}
