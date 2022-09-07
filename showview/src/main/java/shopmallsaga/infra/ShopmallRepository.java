package shopmallsaga.infra;

import java.util.List;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import shopmallsaga.domain.*;

@RepositoryRestResource(collectionResourceRel = "shopmalls", path = "shopmalls")
public interface ShopmallRepository
    extends PagingAndSortingRepository<Shopmall, Long> {
    List<Shopmall> findByOrderId(Long orderId);

    void deleteByOrderId(Long orderId);
    // keep

}
