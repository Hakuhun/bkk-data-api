package hu.oe.bakonyi.bkk.bkkdataapi.scheulder;

import feign.FeignException;
import hu.oe.bakonyi.bkk.bkkdataapi.client.BkkClient;
import hu.oe.bakonyi.bkk.bkkdataapi.kafka.KafkaService;
import hu.oe.bakonyi.bkk.bkkdataapi.service.BkkBusinessDataService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class DataApiSchedurel {

    @Autowired
    KafkaService kafkaService;

    @Autowired
    BkkClient bkkClient;

    @Autowired
    BkkBusinessDataService service;

    @Scheduled(cron = "${scheulder.bkkkScheulder}")
    public void bkkDataScheulder(){
        try {
            sendDataToKafka();
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }
    }

    public void sendDataToKafka(){
        bkkClient.getStoredRoutes().forEach(route ->{
            try{
                kafkaService.sendMessages(service.getData(route.getRouteCode()));
            }catch (FeignException ex){
                log.error("Hiba a viszonylatadatok letöltése közben. {"+route+"}");
            }
        });
    }

}
