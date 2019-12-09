package hu.oe.bakonyi.bkk.bkkdataapi.controller;

import feign.FeignException;
import hu.oe.bakonyi.bkk.bkkdataapi.model.BkkBusinessDataV2;
import hu.oe.bakonyi.bkk.bkkdataapi.scheulder.DataApiSchedurel;
import hu.oe.bakonyi.bkk.bkkdataapi.service.BkkBusinessDataService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Log4j2
@RestController("data")
public class BkkDataControiller {

    @Autowired
    BkkBusinessDataService bkkService;

    @Autowired
    DataApiSchedurel schedurel;

    @GetMapping("dev/route")
    public ResponseEntity<List<BkkBusinessDataV2>> getRoute(@RequestParam("route") String route) {
        try {
            return ResponseEntity.ok(bkkService.getData(route));
        } catch (FeignException fe) {
            log.error(fe.getMessage());
            fe.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @GetMapping("dev/call")
    public ResponseEntity call() {
        schedurel.sendDataToKafka();
        return ResponseEntity.ok("Kafka üzenetek elküldve");
    }
}
