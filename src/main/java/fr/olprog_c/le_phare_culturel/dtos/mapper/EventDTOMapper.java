package fr.olprog_c.le_phare_culturel.dtos.mapper;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Page;

import fr.olprog_c.le_phare_culturel.dtos.event.EventDateDetailsDTO;
import fr.olprog_c.le_phare_culturel.dtos.event.EventDetailReponseDTO;
import fr.olprog_c.le_phare_culturel.dtos.event.EventImagesDTO;
import fr.olprog_c.le_phare_culturel.dtos.event.EventLocationCoordinatesDTO;
import fr.olprog_c.le_phare_culturel.dtos.event.EventLocationPlaceDTO;
import fr.olprog_c.le_phare_culturel.dtos.event.EventResponseDTO;
import fr.olprog_c.le_phare_culturel.entities.EventEntity;
import fr.olprog_c.le_phare_culturel.entities.ImageEntity;
import fr.olprog_c.le_phare_culturel.entities.LocationEntity;
import fr.olprog_c.le_phare_culturel.entities.TTimingEntity;

public class EventDTOMapper {

  public static EventResponseDTO convertPageToResponseDTO(Page<EventEntity> event) {
    List<EventEntity> events = event.getContent();

    if (events == null) {
      return new EventResponseDTO(
          event.getTotalPages(),
          event.getTotalElements(),
          event.getSize(),
          null);
    }

    // events.stream().map();
    return new EventResponseDTO(
        event.getTotalPages(),
        event.getTotalElements(),
        event.getSize(),
        events.stream()
            .map(EventDTOMapper::convertDetailReponseDTO)
            .toList());
  }

  public static EventDetailReponseDTO convertDetailReponseDTO(EventEntity event) {
    return new EventDetailReponseDTO(
        event.getUid(),
        convertImagesDTO(event.getImages()),
        event.getDescription(),
        event.getLongDescription(),
        event.getTitle(),
        convertLocationPlaceDTO(event.getLocation()),
        event.getTarifs(),
        dateRange(event.getFirstTiming(), event.getLastTiming()),
        event.getImageCredits(),
        convertDateDetails(event.getFirstTiming()),
        convertDateDetails(event.getLastTiming())

    );
  }

  private static EventLocationPlaceDTO convertLocationPlaceDTO(LocationEntity location) {
    return new EventLocationPlaceDTO(
        new EventLocationCoordinatesDTO(
            location.getLongitude(),
            location.getLatitude()),
        location.getName(),
        location.getAddress(),
        location.getPostalCode(),
        location.getCity());
  }

  private static EventImagesDTO convertImagesDTO(List<ImageEntity> images) {
    Map<String, String> imagesMap = new HashMap<>();

    for (ImageEntity img : images) {
      if (img != null) {
        if (img.getType().equals("thumbnail")) {
          imagesMap.put("thumbnail", img.getFilename());
        } else {
          imagesMap.put("base", img.getFilename());
        }
      }
    }

    return new EventImagesDTO(
        imagesMap.get("thumbnail"),
        imagesMap.get("base"));

  }

  private static String dateRange(TTimingEntity firstTiming, TTimingEntity lastTiming) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    String first = firstTiming.getBegin().format(formatter);
    String last = lastTiming.getEnd().format(formatter);
    return first + " - " + last;

  }

  private static EventDateDetailsDTO convertDateDetails(TTimingEntity timing) {
    return new EventDateDetailsDTO(
        timing.getBegin(),
        timing.getEnd());
  }

}
