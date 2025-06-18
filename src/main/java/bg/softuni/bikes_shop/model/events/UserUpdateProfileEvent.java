package bg.softuni.bikes_shop.model.events;

import org.springframework.context.ApplicationEvent;

public class UserUpdateProfileEvent extends ApplicationEvent {
    private final String userEmail;
    private final String userFirstName;
    private final String timeOfUpdate;


    public UserUpdateProfileEvent(Object source,String userEmail, String userName, String timeOfUpdate) {
        super(source);
        this.userEmail = userEmail;
        this.userFirstName = userName;

        this.timeOfUpdate = timeOfUpdate;
    }

    public String getUserFirstName() {
        return userFirstName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getTimeOfUpdate() {
        return timeOfUpdate;
    }
}
