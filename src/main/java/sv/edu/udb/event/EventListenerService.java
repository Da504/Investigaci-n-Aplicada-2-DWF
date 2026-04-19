package sv.edu.udb.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EventListenerService {

    @Async
    @EventListener
    public void handleProductoCreatedEvent(ProductoEvent event) {
        try {
            // Simular procesamiento asíncrono
            log.info(" PROCESANDO EVENTO ASÍNCRONO: {}", event.getEventType());

            // Simular tarea en segundo plano (ej: enviar email, notificación, etc)
            Thread.sleep(1000); // Simular tiempo de procesamiento

            log.info(" [Tarea Async] Enviando notificación para producto ID: {} - {}",
                    event.getProductId(), event.getProductName());

            // Aquí podrías: enviar emails, actualizar caché, notificar a otros sistemas, etc.
            if ("CREATE".equals(event.getEventType())) {
                log.info(" Email de creación enviado para: {}", event.getProductName());
            } else if ("UPDATE".equals(event.getEventType())) {
                log.info(" Actualizando caché del producto: {}", event.getProductId());
            } else if ("DELETE".equals(event.getEventType())) {
                log.info(" Limpiando referencias del producto eliminado");
            }

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("Error en procesamiento asíncrono: {}", e.getMessage());
        }
    }

    @Async
    @EventListener
    public void logProductEvent(ProductoEvent event) {
        log.info(" [LOG ASYNC] Registro de auditoría - Evento: {}, Producto: {}, Hora: {}",
                event.getEventType(), event.getProductName(), event.getTimestamp());
    }
}