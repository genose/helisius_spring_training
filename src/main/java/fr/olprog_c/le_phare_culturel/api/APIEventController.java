package fr.olprog_c.le_phare_culturel.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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
    return apiEventService.fetchEvents();
  }

}
