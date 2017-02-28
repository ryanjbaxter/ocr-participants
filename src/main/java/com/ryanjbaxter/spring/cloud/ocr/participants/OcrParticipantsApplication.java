package com.ryanjbaxter.spring.cloud.ocr.participants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.endpoint.HealthEndpoint;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.sleuth.sampler.AlwaysSampler;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableEurekaClient 
public class OcrParticipantsApplication {

    public static void main(String[] args) {
        SpringApplication.run(OcrParticipantsApplication.class, args);
    }

    @Autowired
	public HealthEndpoint healthEndpoint;

    @Bean
	public ParticipantsService getParticipantsService() {
    	return new DefaultParticipantsService();
	}
    
    @Bean
    public AlwaysSampler defaultSampler() {
      return new AlwaysSampler();
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