package fr.olprog_c.le_phare_culturel;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;

import fr.olprog_c.le_phare_culturel.api.models.Event;
import fr.olprog_c.le_phare_culturel.api.services.APIEventService;
import fr.olprog_c.le_phare_culturel.repositories.EventRepository;

@SpringBootApplication
@EnableAsync
public class LePhareCultureApplication {

  private final APIEventService apiEventService;
  private final EventRepository eventRepository;

  public LePhareCultureApplication(APIEventService apiEventService, EventRepository eventRepository) {
    this.apiEventService = apiEventService;
    this.eventRepository = eventRepository;
  }

  public static void main(String[] args) {
    SpringApplication.run(LePhareCultureApplication.class, args);
  }

  @Bean
  public CommandLineRunner run() {
    return args -> {
      if (eventRepository.count() > 0) {
        System.out.println("Events already fetched from API");
        return;
      }
      System.out.println("Fetching events from API....");
      apiEventService.fetchEvents().subscribe(eventResponse -> {
        List<Event> events = List.of(eventResponse.events());
        apiEventService.save(events);
      });

    };
  }

}
