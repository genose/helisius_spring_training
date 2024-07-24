package fr.olprog_c.le_phare_culturel.repositories;

import java.time.OffsetDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import fr.olprog_c.le_phare_culturel.entities.TTimingEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import fr.olprog_c.le_phare_culturel.entities.EventEntity;

public interface EventRepository extends JpaRepository<EventEntity, Long> {
    @Query(value = "SELECT * FROM events ORDER BY RAND() LIMIT 1", nativeQuery = true)
    Optional<EventEntity> findRandomEvent();

    // filtrer les événements par lasttiming l'attribut end
    Page<EventEntity> findAllByOrderByLastTiming_endAsc(Pageable pageable);

    @Query(value = "SELECT * FROM events e inner join timings t on (t.id = e.first_timing_id  or t.id = e.last_timing_id) WHERE t.id IN :ids " +
            "AND (t.begin >= NOW() AND t.end > NOW() )")
    Optional<List<EventEntity>> findFutureEventsWithIds(List<Long> ids);

    @Query(value = "SELECT * FROM events e inner join timings t on (t.id = e.first_timming_id  or t.id = e.last_timming_id) WHERE e.id IN :ids" +
            " AND (t.begin >= :firstTiming AND t.end < :lastTiming)")
    Optional<List<EventEntity>> findBetweenDatesEvents(List<Long> ids, OffsetDateTime firstTiming, OffsetDateTime lastTiming);

    @Query(value = "SELECT * FROM events e inner join timings t on (t.id = e.first_timing_id  or t.id = e.last_timing_id) WHERE t.id IN :ids AND (t.begin <= NOW() AND t.end < NOW() )")
    Optional<List<EventEntity>> findExpiredEventsWithIds(List<Long> ids);
}
