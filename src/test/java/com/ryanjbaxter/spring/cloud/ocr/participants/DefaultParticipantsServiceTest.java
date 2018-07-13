package com.ryanjbaxter.spring.cloud.ocr.participants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author Ryan Baxter
 */
public class DefaultParticipantsServiceTest {

	private List<Participant> participants;

	@Before
	public void setup() {
		this.participants = new ArrayList<>();
		this.participants.add(new Participant("Ryan", "Baxter", "MA", "S", Arrays.asList("123", "456")));
		this.participants.add(new Participant("Stephanie", "Baxter", "MA", "S", Arrays.asList("456")));
	}

	@After
	public void tearDown() {
		this.participants = new ArrayList<>();
	}

	@Test
	public void getAllParticipantsTest() {
		DefaultParticipantsService participantsService = new DefaultParticipantsService();
		assertEquals(participants, participantsService.getParticipants().collectList().block());
	}

	@Test
	public void getAllParticipantsWithRaceTest() {
		DefaultParticipantsService participantsService = new DefaultParticipantsService();
		assertTrue(participantsService.getParticipants("1234523").collectList().block().isEmpty());
		assertEquals(Arrays.asList(this.participants.get(0)), participantsService.getParticipants("123").collectList().block());
		assertEquals(this.participants, participantsService.getParticipants("456").collectList().block());
	}
}
