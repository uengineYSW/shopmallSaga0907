package shopmallsaga.domain;

import java.util.*;
import lombok.*;
import shopmallsaga.domain.*;
import shopmallsaga.infra.AbstractEvent;

@Data
@ToString
public class OrderCancelled extends AbstractEvent {

    private Long id;
    // keep

}
