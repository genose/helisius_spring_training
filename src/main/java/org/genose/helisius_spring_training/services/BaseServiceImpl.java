package org.genose.helisius_spring_training.services;

import jakarta.validation.constraints.PositiveOrZero;
import org.genose.helisius_spring_training.dtos.BaseGetResponseDTO;
import org.genose.helisius_spring_training.entities.BaseCommonEntity;
import org.genose.helisius_spring_training.repositories.BaseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public abstract class BaseServiceImpl {
    /* ****** ****** ****** ****** */
    protected Logger logger = LoggerFactory.getLogger(BaseServiceImpl.class);

    /* ****** ****** ****** ****** */
    /* ****** ****** ****** ****** */
    public <S> S save(S entity) {
        return null;
    }

    public <S extends BaseCommonEntity, T extends BaseRepository> List<T> fetchEntitiesAndConvertToDto(
            T repository,
            Function<S, T> mapper) {
        return (List<T>) StreamSupport.stream(repository.findAll().spliterator(), false)
                .map(mapper)
                .collect(Collectors.toList());
    }

    public <S extends BaseCommonEntity> Iterable<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @PositiveOrZero
    public <S extends BaseGetResponseDTO> Optional<S> findById(Integer id) {
        return Optional.empty();
    }


    public boolean existsById(Integer integer) {
        return false;
    }


    public Iterable<?> findAll() {
        return null;
    }


    public Iterable<?> findAllById(Iterable<Integer> integers) {
        return null;
    }


    public long count() {
        return 0;
    }


    public void deleteById(Integer integer) {

    }


    public void delete(Object entity) {

    }


    public void deleteAllById(Iterable<? extends Integer> integers) {

    }


    public void deleteAll(Iterable<?> entities) {

    }


    public void deleteAll() {

    }

}
