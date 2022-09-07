package shopmallsaga.domain;

import java.util.*;
import lombok.*;
import shopmallsaga.domain.*;
import shopmallsaga.infra.AbstractEvent;

@Data
@ToString
public class DeliveryCancelled extends AbstractEvent {

    private Long id;
    private Long orderId;

    public DeliveryCancelled(Delivery aggregate) {
        super(aggregate);
    }

    public DeliveryCancelled() {
        super();
    }
    // keep

}
