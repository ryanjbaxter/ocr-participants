package com.ryanjbaxter.spring.cloud.ocr.participants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Ryan Baxter
 */
public class DefaultParticipantsService implements ParticipantsService {

	private static List<Participant> participants = new ArrayList<Participant>();
	static{
		participants.add(new Participant("Ryan", "Baxter", "MA", "S", Arrays.asList("123", "456")));
		participants.add(new Participant("Stephanie", "Baxter", "MA", "S", Arrays.asList("456")));
	}

	@Override
	public List<Participant> getParticipants() {
		return participants;
	}

	@Override
	public List<Participant> getParticipants(String raceId) {
		return participants.stream().filter(p -> p.getRaces().contains(raceId)).collect(Collectors.toList());
	}
}
