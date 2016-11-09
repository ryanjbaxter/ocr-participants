package com.ryanjbaxter.spring.cloud.ocr.participants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.endpoint.HealthEndpoint;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.sleuth.sampler.AlwaysSampler;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
@EnableEurekaClient 
public class OcrParticipantsApplication implements CommandLineRunner {
	
	private static List<Participant> participants = new ArrayList<Participant>();

    public static void main(String[] args) {
        SpringApplication.run(OcrParticipantsApplication.class, args);
    }

    @Autowired
	public HealthEndpoint healthEndpoint;
    
    @Bean
    public AlwaysSampler defaultSampler() {
      return new AlwaysSampler();
    }

	@Override
	public void run(String... arg0) throws Exception {
		participants.add(new Participant("Ryan", "Baxter", "MA", "S", Arrays.asList("123", "456")));
		participants.add(new Participant("Stephanie", "Baxter", "MA", "S", Arrays.asList("456")));		
	}
	
	@RequestMapping("/")
	public List<Participant> getParticipants() {
		if(!healthEndpoint.invoke().getStatus().equals(Status.UP)) {
			throw new OutOfServiceException();
		}
		return participants;
	}
	
	@RequestMapping("/races/{id}")
	public List<Participant> getParticipants(@PathVariable String id) {
		if(!healthEndpoint.invoke().getStatus().equals(Status.UP)) {
			throw new OutOfServiceException();
		}
		return participants.stream().filter(p -> p.getRaces().contains(id)).collect(Collectors.toList());
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

class Participant {
	private String firstName;
	private String lastName;
	private String homeState;
	private String shirtSize;
	private List<String> races;
	public Participant(String firstName, String lastName, String homeState,
			String shirtSize, List<String> races) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.homeState = homeState;
		this.shirtSize = shirtSize;
		this.races = races;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getHomeState() {
		return homeState;
	}
	public void setHomeState(String homeState) {
		this.homeState = homeState;
	}
	public String getShirtSize() {
		return shirtSize;
	}
	public void setShirtSize(String shirtSize) {
		this.shirtSize = shirtSize;
	}
	public List<String> getRaces() {
		return races;
	}
	public void setRaces(List<String> races) {
		this.races = races;
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