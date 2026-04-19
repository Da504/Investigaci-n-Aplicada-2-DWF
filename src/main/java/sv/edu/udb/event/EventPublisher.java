package sv.edu.udb.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class EventPublisher {

    private final ApplicationEventPublisher applicationEventPublisher;

    public EventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void publishProductEvent(String eventType, Long productId, String productName, String action) {
        ProductoEvent event = new ProductoEvent(
                eventType,
                productId,
                productName,
                action,
                java.time.LocalDateTime.now(),
                "Evento: " + action + " del producto " + productName
        );

        applicationEventPublisher.publishEvent(event);
        log.info(" Evento publicado: {} - Producto: {}", eventType, productName);
    }
}