package org.genose.helisius_spring_training.services;

import jakarta.validation.constraints.PositiveOrZero;
import org.genose.helisius_spring_training.dtos.BaseGetResponseDTO;
import org.genose.helisius_spring_training.entities.BaseCommonEntity;
import org.genose.helisius_spring_training.mapper.BaseMapperEntity;
import org.genose.helisius_spring_training.repositories.BaseRepository;
import org.genose.helisius_spring_training.utils.GNSClassStackUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

public abstract class BaseServiceImpl {
    /* ****** ****** ****** ****** */
    protected Logger logger = LoggerFactory.getLogger(BaseServiceImpl.class);

    /* ****** ****** ****** ****** */
    /* ****** ****** ****** ****** */
    public <S> S save(S entity) {
        return null;
    }

    public <
            S extends BaseCommonEntity,
            T extends BaseGetResponseDTO,
            W extends BaseRepository<? extends BaseCommonEntity, Integer>
            >
    List<T> fetchEntitiesAndConvertToDto(
            Class<? extends BaseCommonEntity> inputEntityClass,
            Class<? extends BaseGetResponseDTO> responseDtoClass,
            Class<? extends BaseRepository<? extends BaseCommonEntity, Integer>> repositoryClass
    ) {
        try {
            if (repositoryClass == null) {
                throw new IllegalArgumentException("Repository cannot be null");
            }
            W newRepository = (W) repositoryClass.getDeclaredConstructor().newInstance();
            List<S> entityList = (List<S>) newRepository.findAll();

            return entityList.stream()
                    .map(entity -> (T) BaseMapperEntity
                            .convertFromEntityToDTO(entity, (Class<? extends BaseGetResponseDTO>) repositoryClass)
                    )
                    .toList();

        } catch (Exception e) {
            logger.error(this.getClass().getSimpleName() + " :: " +
                    GNSClassStackUtils.getEnclosingMethodObject(this) + " :: " + e.getMessage());
        }
        return null;
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
