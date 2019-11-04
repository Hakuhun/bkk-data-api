package hu.oe.bakonyi.bkk.bkkdataapi.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import hu.oe.bakonyi.bkk.bkkdataapi.model.BkkBusinessDataV2;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Log4j2
@Service
public class KafkaService {
    private static final String TOPIC = "bkk";

    @Autowired
    private KafkaTemplate<String, BkkBusinessDataV2> kafkaTemplate;

    @Autowired
    private ObjectMapper mapper;

    public void sendMessages(List<BkkBusinessDataV2> messages){
        messages.forEach(msg->{
            sendMessage(msg);
        });
    }

    public void sendMessage(BkkBusinessDataV2 msg){
        log.info(String.format("$$ -> Producing message --> %s",msg));
        this.kafkaTemplate.send(TOPIC, UUID.randomUUID().toString(), msg);
    }

}
