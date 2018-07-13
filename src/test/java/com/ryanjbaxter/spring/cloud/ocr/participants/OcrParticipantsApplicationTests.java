package com.ryanjbaxter.spring.cloud.ocr.participants;

import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class OcrParticipantsApplicationTests {

	@Autowired
	private WebTestClient webTestClient;

	@Test
	public void testGetAllParticipantsEndpoint() {
		List<Participant> participantList = new DefaultParticipantsService().getParticipantsAsList();
		webTestClient.get().uri("/").exchange().expectStatus().isOk().expectBody(Participant[].class).
				isEqualTo(new DefaultParticipantsService().getParticipantsAsArray());
	}

	@Test
	public void testGetAllParticipantsByRaceEndpoint() {
		webTestClient.get().uri("/races/456").exchange().expectStatus().isOk().expectBody(Participant[].class).
				isEqualTo(new DefaultParticipantsService().getParticipantsAsArray("456"));
		webTestClient.get().uri("/races/123").exchange().expectStatus().isOk().expectBody(Participant[].class).
				isEqualTo(new DefaultParticipantsService().getParticipantsAsArray("123"));
	}

}
