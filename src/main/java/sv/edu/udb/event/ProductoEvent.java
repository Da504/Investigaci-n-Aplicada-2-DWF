package sv.edu.udb.event;

import lombok.Getter;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ProductoEvent {
    private String eventType;     // CREATE, UPDATE, DELETE
    private Long productId;
    private String productName;
    private String action;
    private LocalDateTime timestamp;
    private String message;
}