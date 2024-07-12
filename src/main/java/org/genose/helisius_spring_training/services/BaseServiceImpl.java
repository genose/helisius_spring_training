package org.genose.helisius_spring_training.services;

import jakarta.validation.constraints.PositiveOrZero;
import org.genose.helisius_spring_training.dtos.BaseGetResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BaseServiceImpl {
    /* ****** ****** ****** ****** */
    protected Logger logger = LoggerFactory.getLogger(BaseServiceImpl.class);

    /* ****** ****** ****** ****** */
    /* ****** ****** ****** ****** */
    public <S> S save(S entity) {
        return null;
    }

    /* public <S extends BaseCommonEntity, T, Long, ID> List<T> fetchEntitiesAndConvertToDto(  ? extends BaseRepository  repository, Function<S, T> mapper) {
        return StreamSupport.stream(repository.findAll().spliterator(), false)
                .map(mapper)
                .collect(Collectors.toList());
    }*/

    public <S> Iterable<S> saveAll(Iterable<S> entities) {
        return null;
    }

    /*
    @PositiveOrZero
    public <S> Optional<S extends BaseGetResponseDTO> findById(Integer integer) {
        return Optional.empty();
    } */

    @PositiveOrZero
    public <S extends BaseGetResponseDTO> S findById(Integer id) {
        return null;
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
