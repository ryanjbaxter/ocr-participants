package com.ryanjbaxter.spring.cloud.ocr.participants.base;

import org.junit.Before;
import org.springframework.boot.actuate.endpoint.HealthEndpoint;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.Status;
import com.jayway.restassured.module.mockmvc.RestAssuredMockMvc;
import com.ryanjbaxter.spring.cloud.ocr.participants.DefaultParticipantsService;
import com.ryanjbaxter.spring.cloud.ocr.participants.ParticipantsController;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

/**
 * @author Ryan Baxter
 */
public class ParticipantsBase {
	@Before
	public void setup() {
		HealthEndpoint healthEndpoint = mock(HealthEndpoint.class);
		Health okHealth = Health.up().build();
		doReturn(okHealth).when(healthEndpoint).invoke();
		RestAssuredMockMvc.standaloneSetup(new ParticipantsController(healthEndpoint, new DefaultParticipantsService()));
	}
}
