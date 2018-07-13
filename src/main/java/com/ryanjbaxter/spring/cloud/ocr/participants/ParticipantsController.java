package com.ryanjbaxter.spring.cloud.ocr.participants;

import reactor.core.publisher.Flux;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.boot.actuate.health.HealthEndpoint;
import org.springframework.boot.actuate.health.Status;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Ryan Baxter
 */
@RestController
public class ParticipantsController {

	private HealthEndpoint healthEndpoint;
	private ParticipantsService participantsService;

	public ParticipantsController(HealthEndpoint healthEndpoint, ParticipantsService participantsService) {
		this.healthEndpoint = healthEndpoint;
		this.participantsService = participantsService;
	}
	@RequestMapping("/")
	public Flux<Participant> getParticipants() {
		if(!healthEndpoint.health().getStatus().equals(Status.UP)) {
			throw new OutOfServiceException();
		}
		return participantsService.getParticipants();
	}

	@RequestMapping("/races/{id}")
	public Flux<Participant> getParticipants(@PathVariable String id) {
		if(!healthEndpoint.health().getStatus().equals(Status.UP)) {
			throw new OutOfServiceException();
		}
		return participantsService.getParticipants(id);
	}

	@RequestMapping("/slow")
	public Flux<Participant> getSlowParticipants() {
		return getParticipants();
	}

	@RequestMapping("/slow/races/{id}")
	public Flux<Participant> getSlowParticipants(@PathVariable String id) {
		return getParticipants(id).delayElements(Duration.of(80000, ChronoUnit.MILLIS));
	}


	@ResponseStatus(value= HttpStatus.INTERNAL_SERVER_ERROR, reason="Server unhealthy")
	static class OutOfServiceException extends RuntimeException {}

}
