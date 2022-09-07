package shopmallsaga.domain;

import java.util.Date;
import lombok.Data;
import shopmallsaga.infra.AbstractEvent;

@Data
public class OrderCancelled extends AbstractEvent {

    private Long id;
}
