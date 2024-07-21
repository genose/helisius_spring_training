package fr.olprog_c.le_phare_culturel.api.services;

import java.util.List;

import org.springframework.security.core.Transient;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import fr.olprog_c.le_phare_culturel.api.Mapper;
import fr.olprog_c.le_phare_culturel.api.models.Event;
import fr.olprog_c.le_phare_culturel.api.models.EventResponse;
import fr.olprog_c.le_phare_culturel.repositories.EventRepository;
import reactor.core.publisher.Mono;

@Service
@Transient
public class APIEventService {

  private final WebClient webClient;
  private final EventRepository eventRepository;

  public APIEventService(WebClient webClient, EventRepository eventRepository) {
    this.webClient = webClient;
    this.eventRepository = eventRepository;
  }

  public Mono<EventResponse> fetchEvents() {
    return webClient.get()
        .uri(
            "https://api.openagenda.com/v2/agendas/57621068/events?key=dd5c193e8af34e65b7654f7ba60b3e4f&size=300&timings[gte]=2024-07-21T00:00:00.000Z&monolingual=fr&detailed=1")
        .retrieve()
        .bodyToMono(EventResponse.class);
  }

  public void save(List<Event> event) {
    // Save the events in the database
    try {
      event.stream().map(Mapper::apiToEntity).forEach(eventRepository::save);
    } catch (Exception e) {
      System.out.println("Error saving events: " + e.getMessage());

    }
  }
}
