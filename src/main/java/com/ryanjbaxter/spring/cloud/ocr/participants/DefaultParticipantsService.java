package com.ryanjbaxter.spring.cloud.ocr.participants;

import reactor.core.publisher.Flux;

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

	public List<Participant> getParticipantsAsList() {
		return participants;
	}

	public Participant[] getParticipantsAsArray() {
		return participants.toArray(new Participant[participants.size()]);
	}

	public Participant[] getParticipantsAsArray(String raceId) {
		List<Participant> participantList = participants.stream().filter(p -> p.getRaces().contains(raceId)).collect(Collectors.toList());
		return participantList.toArray(new Participant[participantList.size()]);
	}

	@Override
	public Flux<Participant> getParticipants() {
		return Flux.fromIterable(getParticipantsAsList());
	}

	@Override
	public Flux<Participant> getParticipants(String raceId) {
		return Flux.fromStream(participants.stream().filter(p -> p.getRaces().contains(raceId)));
	}
}
