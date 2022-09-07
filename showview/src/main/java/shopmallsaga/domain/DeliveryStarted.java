package shopmallsaga.domain;

import java.util.Date;
import lombok.Data;
import shopmallsaga.infra.AbstractEvent;

@Data
public class DeliveryStarted extends AbstractEvent {

    private Long id;
    private Long orderId;
    private Long productId;
    private String productName;
}
