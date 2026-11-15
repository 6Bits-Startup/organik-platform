package _5bits.organik.platform.products.application.internal.eventhandlers;

import _5bits.organik.platform.products.application.commandservices.CategoryCommandService;
import _5bits.organik.platform.products.domain.model.commands.SeedCategoriesCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

/**
 * Application lifecycle handler that ensures the default product categories are seeded when the application is ready.
 */
@Service("productsApplicationReadyEventHandler")
@Slf4j
public class ApplicationReadyEventHandler {
    private final CategoryCommandService categoryCommandService;

    public ApplicationReadyEventHandler(CategoryCommandService categoryCommandService) {
        this.categoryCommandService = categoryCommandService;
    }

    /**
     * Handles the Spring application-ready event and triggers category seeding verification.
     *
     * @param event Spring Boot readiness event
     */
    @EventListener
    public void on(ApplicationReadyEvent event) {
        var applicationName = event.getApplicationContext().getId();
        log.info("Starting to verify if categories seeding is needed for {} at {}", applicationName, currentTimestamp());
        var seedCategoriesCommand = new SeedCategoriesCommand();
        categoryCommandService.handle(seedCategoriesCommand);
        log.info("Categories seeding verification finished for {} at {}", applicationName, currentTimestamp());
    }

    private Timestamp currentTimestamp() {
        return new Timestamp(System.currentTimeMillis());
    }
}
