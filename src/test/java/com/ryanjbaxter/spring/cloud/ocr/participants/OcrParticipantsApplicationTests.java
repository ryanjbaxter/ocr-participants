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

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class OcrParticipantsApplicationTests {

	@Autowired
	private TestRestTemplate rest;

	@Test
	public void testGetAllParticipantsEndpoint() {
		List<Participant> participants = Arrays.asList(rest.getForObject("/", Participant[].class));
		assertEquals(new DefaultParticipantsService().getParticipants(), participants);
	}

	@Test
	public void testGetAllParticipantsByRaceEndpoint() {
		List<Participant> participants = Arrays.asList(rest.getForObject("/races/456", Participant[].class));
		assertEquals(new DefaultParticipantsService().getParticipants("456"), participants);
		participants = Arrays.asList(rest.getForObject("/races/123", Participant[].class));
		assertEquals(new DefaultParticipantsService().getParticipants("123"), participants);
	}

}
