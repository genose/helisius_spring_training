package fr.olprog_c.le_phare_culturel.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.olprog_c.le_phare_culturel.EventFiltersSpecification;
import fr.olprog_c.le_phare_culturel.dtos.event.EventEntityGETResponseDTO;
import fr.olprog_c.le_phare_culturel.entities.EventEntity;
import fr.olprog_c.le_phare_culturel.repositories.EventRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class EventService {
    private final EventRepository eventRepository;
    private final ObjectMapper objectMapper;

    /**
     * Event Service constructor, injecting the dependent services.
     *
     * @param eventRepository Instance of Event Repository.
     * @param objectMapper    Instance of Object Mapper.
     */
    public EventService(EventRepository eventRepository, ObjectMapper objectMapper) {
        this.eventRepository = eventRepository;
        this.objectMapper = objectMapper;
    }

    /**
     * This method fetches paged results of EventEntity.
     *
     * @param pageNumber Integer specifying zero based page index, must not be negative.
     * @param pageSize   Integer specifying the size of the page to be returned, must not be negative.
     * @return List of EventEntity.
     */
    public List<EventEntity> findAllInLimit(int pageNumber, int pageSize) {
        pageNumber = Math.max(pageNumber, 0);
        pageSize = Math.max(pageSize, 50);
        return eventRepository.findAll(PageRequest.of(pageNumber, pageSize)).getContent();
    }

    /**
     * This method fetches EventEntity by its ID.
     *
     * @param id Integer specifying the ID of the event, must not be negative.
     * @return EventEntity instance if found, else null.
     */
    public EventEntity findByID(int id) {
        return eventRepository.findById(Math.max(0, id)).orElse(null);
    }

    /**
     * This method fetches paged DTOs of EventEntityGETResponseDTO converted from EventEntity.
     *
     * @param pageNumber Integer specifying zero based page index, must not be negative.
     * @param pageSize   Integer specifying the size of the page to be returned, must not be negative.
     * @return List of EventEntityGETResponseDTO.
     */
    public List<EventEntityGETResponseDTO> findAllInLimitDTO(int pageNumber, int pageSize) {
        pageNumber = Math.max(pageNumber, EventParametersConstants.DEFAULT_PAGE_OFFSET.getIntegerValue());
        pageSize = Math.max(pageSize, EventParametersConstants.DEFAULT_PAGE_SIZE.getIntegerValue());
        List<EventEntity> events = findAllInLimit(pageNumber, pageSize);
        List<EventEntityGETResponseDTO> getResponseDTOListdtos = new ArrayList<>();
        for (EventEntity event : events) {
            EventEntityGETResponseDTO dto = objectMapper.convertValue(event, EventEntityGETResponseDTO.class);
            getResponseDTOListdtos.add(dto);
        }
        return getResponseDTOListdtos;
    }

    /**
     * This method fetches filtered and paged DTOs of EventEntityGETResponseDTO converted from EventEntity.
     *
     * @param pageNumber Integer specifying zero based page index, must not be negative.
     * @param pageSize   Integer specifying the size of the page to be returned, must not be negative.
     * @param filters    Key-Value pair of attributes and their values to filter the events by.
     * @return List of EventEntityGETResponseDTO.
     */
    public List<EventEntityGETResponseDTO> findAllByFiltering(int pageNumber, int pageSize, Map<String, String> filters) {
        pageNumber = Math.max(pageNumber, 0);
        pageSize = Math.max(pageSize, EventParametersConstants.DEFAULT_PAGE_SIZE.getIntegerValue());
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, Sort.by("id").descending());

        final Specification<EventEntity> filtersSpecificationPredicates =
                EventFiltersSpecification.getFiltersSpecificationPredicates(filters);

        List<EventEntity> filteredEventList = eventRepository.findEventsBySpecification(filtersSpecificationPredicates).subList(pageNumber * pageSize, (1 + pageNumber) * pageSize);

        List<EventEntityGETResponseDTO> getResponseDTOListdtos = new ArrayList<>();
        for (EventEntity event : filteredEventList) {
            EventEntityGETResponseDTO dto = objectMapper.convertValue(event, EventEntityGETResponseDTO.class);
            getResponseDTOListdtos.add(dto);
        }
        return getResponseDTOListdtos;
    }
}
