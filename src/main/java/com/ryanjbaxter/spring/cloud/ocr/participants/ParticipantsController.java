package com.ryanjbaxter.spring.cloud.ocr.participants;

import java.util.List;

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
	public List<Participant> getParticipants() {
		if(!healthEndpoint.health().getStatus().equals(Status.UP)) {
			throw new OutOfServiceException();
		}
		return participantsService.getParticipants();
	}

	@RequestMapping("/races/{id}")
	public List<Participant> getParticipants(@PathVariable String id) {
		if(!healthEndpoint.health().getStatus().equals(Status.UP)) {
			throw new OutOfServiceException();
		}
		return participantsService.getParticipants(id);
	}

	@RequestMapping("/slow")
	public List<Participant> getSlowParticipants() {
		return getParticipants();
	}

	@RequestMapping("/slow/races/{id}")
	public List<Participant> getSlowParticipants(@PathVariable String id) {
		try {
			Thread.sleep(80000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return getParticipants(id);
	}


	@ResponseStatus(value= HttpStatus.INTERNAL_SERVER_ERROR, reason="Server unhealthy")
	static class OutOfServiceException extends RuntimeException {}

}
