package com.ryanjbaxter.spring.cloud.ocr.participants;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class OcrParticipantsApplication {

    public static void main(String[] args) {
        SpringApplication.run(OcrParticipantsApplication.class, args);
    }

    @Bean
	public ParticipantsService getParticipantsService() {
    	return new DefaultParticipantsService();
	}

}

@RestController
class MyHealth implements HealthIndicator {

	private Status status = Status.UP;

	@RequestMapping("/healthy")
	public Status healthy() {
		this.status = Status.UP;
		return status;
	}

	@RequestMapping("/sick")
	public Status sick() {
		this.status = Status.DOWN;
		return status;
	}

	@RequestMapping("/outofserice")
	public Status outOfService() {
		this.status = Status.OUT_OF_SERVICE;
		return status;
	}

	@Override
	public Health health() {
		return Health.status(status).build();
	}
}