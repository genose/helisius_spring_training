package fr.olprog_c.le_phare_culturel.api;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.olprog_c.le_phare_culturel.api.models.Event;
import fr.olprog_c.le_phare_culturel.api.models.EventResponse;
import fr.olprog_c.le_phare_culturel.api.services.APIEventService;
import reactor.core.publisher.Mono;

@RestController
public class APIEventController {

  private final APIEventService apiEventService;

  public APIEventController(APIEventService apiEventService) {
    this.apiEventService = apiEventService;
  }

  @GetMapping("/get-events")
  public Mono<EventResponse> getEvents() {
    Mono<EventResponse> response = apiEventService.fetchEvents();
    response.subscribe(eventResponse -> {
      List<Event> events = List.of(eventResponse.events());
      apiEventService.save(events);
    });
    return response;
  }
}
