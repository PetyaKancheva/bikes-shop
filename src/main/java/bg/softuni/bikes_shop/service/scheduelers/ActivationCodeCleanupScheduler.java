package bg.softuni.bikes_shop.service.scheduelers;

import bg.softuni.bikes_shop.service.UserActivationService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class ActivationCodeCleanupScheduler {
    private final UserActivationService userActivationService;

    public ActivationCodeCleanupScheduler(UserActivationService userActivationService) {
        this.userActivationService = userActivationService;
    }

        @Scheduled(cron="0 5 5 * * *") //every day at 5:05
//    @Scheduled(cron ="0 */1 */1 * * *")// every 1 min
    public void cleanUp(){
        userActivationService.cleanUpObsoleteActivationLinks();
        System.out.println("Activation cleanup service *** " + Instant.now() +" ***");
    }
}
