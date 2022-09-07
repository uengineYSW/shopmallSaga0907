package shopmallsaga.domain;

import java.util.*;
import lombok.*;
import shopmallsaga.domain.*;
import shopmallsaga.infra.AbstractEvent;

@Data
@ToString
public class OrderPlaced extends AbstractEvent {

    private Long id;
    private Long productId;
    private Integer qty;
    private String productName;
    // keep

}
