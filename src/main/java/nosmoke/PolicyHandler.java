package nosmoke;

import nosmoke.config.kafka.KafkaProcessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import nosmoke.external.Earn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PolicyHandler{
    @StreamListener(KafkaProcessor.INPUT)
    public void onStringEventListener(@Payload String eventString){

    }

    @Autowired
    HealthRepository HealthRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverBuryed_Bury(@Payload Buryed buryed){

        if(buryed.isMe()){

            Optional<Health> healthOptional = HealthRepository.findById(buryed.getHealthId());
            Health health = healthOptional.get();
            health.setPoint(buryed.getPoint());
            health.setStatus("EARNED");


            HealthRepository.save(health);
        }
    }

}
