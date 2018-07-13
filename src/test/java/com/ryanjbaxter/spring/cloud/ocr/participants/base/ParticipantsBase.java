package com.ryanjbaxter.spring.cloud.ocr.participants.base;

import io.restassured.RestAssured;

import org.junit.Before;

import org.junit.runner.RunWith;
import org.springframework.boot.actuate.health.HealthEndpoint;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit4.SpringRunner;

import com.ryanjbaxter.spring.cloud.ocr.participants.DefaultParticipantsService;
import com.ryanjbaxter.spring.cloud.ocr.participants.ParticipantsController;
import com.ryanjbaxter.spring.cloud.ocr.participants.ParticipantsService;

/**
 * @author Ryan Baxter
 */
@SpringBootTest(classes = ParticipantsBase.Config.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = {
		"spring.profiles.active:test"})
@RunWith(SpringRunner.class)
public class ParticipantsBase {

	@LocalServerPort
	int port;

	@Before
	public void setup() {
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = this.port;
	}

	@Configuration
	@EnableAutoConfiguration
	static class Config {

		@Bean
		public ParticipantsController participantsController(HealthEndpoint healthEndpoint, ParticipantsService participantsService) {
			return new ParticipantsController(healthEndpoint, participantsService);
		}

		@Bean
		public ParticipantsService participantsService() {
			return new DefaultParticipantsService();
		}
	}
}
