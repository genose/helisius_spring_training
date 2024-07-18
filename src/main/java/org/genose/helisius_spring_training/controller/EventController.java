package org.genose.helisius_spring_training.controller;

import org.genose.helisius_spring_training.controller.routes.RouteDefinitions;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(RouteDefinitions.EVENTS_URL)
public class EventController extends BaseRoutesController {

}
