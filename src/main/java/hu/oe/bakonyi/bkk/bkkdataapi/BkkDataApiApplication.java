package hu.oe.bakonyi.bkk.bkkdataapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableKafka
@EnableScheduling
@ComponentScan("hu.oe.bakonyi")
@EnableFeignClients
public class BkkDataApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(BkkDataApiApplication.class, args);
	}

}
