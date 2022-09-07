package shopmallsaga.external;

import java.util.Date;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "order", url = "${api.url.order}")
public interface OrderService {
    @RequestMapping(method = RequestMethod.DELETE, path = "/orders")
    public void cancelOrder(@RequestBody Order order);
    // keep

}
