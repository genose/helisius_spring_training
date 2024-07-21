package fr.olprog_c.le_phare_culturel.api.services;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import fr.olprog_c.le_phare_culturel.api.models.EventResponse;
import reactor.core.publisher.Mono;

@Service
public class APIEventService {

  private final WebClient webClient;

  public APIEventService(WebClient webClient) {
    this.webClient = webClient;
  }

  public Mono<EventResponse> fetchEvents() {
    return webClient.get()
        .uri("https://api.openagenda.com/v2/agendas/57621068/events?key=dd5c193e8af34e65b7654f7ba60b3e4f")
        .retrieve()
        .bodyToMono(EventResponse.class);
  }
}
