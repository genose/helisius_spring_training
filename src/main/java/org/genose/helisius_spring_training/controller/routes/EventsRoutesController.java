package org.genose.helisius_spring_training.controller.routes;

import org.genose.helisius_spring_training.entities.UserEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/events")
public class EventsRoutesController extends BaseRoutesController {
	@GetMapping("/auth")
	public ResponseEntity<?> index(@AuthenticationPrincipal final UserEntity argUser) {
		return ResponseEntity.ok().body("Liste d events");
	}
}
