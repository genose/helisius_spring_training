package fr.olprog_c.le_phare_culturel.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.olprog_c.le_phare_culturel.entities.EventEntity;

public interface EventRepository extends JpaRepository<EventEntity, Long> {
}
