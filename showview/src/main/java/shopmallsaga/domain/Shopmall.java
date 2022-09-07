package shopmallsaga.domain;

import java.util.Date;
import java.util.List;
import javax.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Shopmall_table")
@Data
public class Shopmall {

    @Id
    //@GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    private Long orderId;
    private Long productId;
    private Integer qty;
    private Long deliveryId;
}
