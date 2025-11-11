package bg.softuni.bikes_shop.service.scheduelers;

import bg.softuni.bikes_shop.service.UserActivationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class ActivationCodeCleanupScheduler {
    private final UserActivationService userActivationService;
    private static final Logger logger = LoggerFactory.getLogger(ActivationCodeCleanupScheduler.class);

    public ActivationCodeCleanupScheduler(UserActivationService userActivationService) {
        this.userActivationService = userActivationService;
    }

        @Scheduled(cron="0 5 5 * * *") //every day at 5:05
//    @Scheduled(cron ="0 */1 */1 * * *")// every 1 min
    public void cleanUp(){
        userActivationService.cleanUpObsoleteActivationLinks();
        logger.info("Activation cleanup service done ***");
    }
}
