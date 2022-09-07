package shopmallsaga.infra;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import shopmallsaga.config.kafka.KafkaProcessor;
import shopmallsaga.domain.*;

@Service
public class ShopmallViewHandler {

    @Autowired
    private ShopmallRepository shopmallRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whenOrderPlaced_then_CREATE_1(
        @Payload OrderPlaced orderPlaced
    ) {
        try {
            if (!orderPlaced.validate()) return;

            // view 객체 생성
            Shopmall shopmall = new Shopmall();
            // view 객체에 이벤트의 Value 를 set 함
            shopmall.setOrderId(orderPlaced.getId());
            shopmall.setProductId(orderPlaced.getProductId());
            shopmall.setQty(orderPlaced.getQty());
            // view 레파지 토리에 save
            shopmallRepository.save(shopmall);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void whenDeliveryStarted_then_UPDATE_1(
        @Payload DeliveryStarted deliveryStarted
    ) {
        try {
            if (!deliveryStarted.validate()) return;
            // view 객체 조회

            List<Shopmall> shopmallList = shopmallRepository.findByOrderId(
                deliveryStarted.getOrderId()
            );
            for (Shopmall shopmall : shopmallList) {
                // view 객체에 이벤트의 eventDirectValue 를 set 함
                shopmall.setDeliveryId(deliveryStarted.getId());
                // view 레파지 토리에 save
                shopmallRepository.save(shopmall);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void whenOrderCancelled_then_DELETE_1(
        @Payload OrderCancelled orderCancelled
    ) {
        try {
            if (!orderCancelled.validate()) return;
            // view 레파지 토리에 삭제 쿼리
            shopmallRepository.deleteByOrderId(orderCancelled.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // keep

}
