package shopmallsaga.external;

import java.util.Date;
import lombok.Data;

@Data
public class Order {

    private Long id;
    private Long productId;
    private Integer qty;
    private String productName;
    // keep

}
