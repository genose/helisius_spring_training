package org.genose.helisius_spring_training.controller;

import org.genose.helisius_spring_training.controller.routes.RouteDefinitions;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(RouteDefinitions.GROUPS_URL)
public class GroupController extends BaseRoutesController {

}
