package org.genose.helisius_spring_training.services;

import jakarta.validation.constraints.PositiveOrZero;
import org.genose.helisius_spring_training.dtos.BaseGetResponseDTO;
import org.genose.helisius_spring_training.dtos.BaseResponseRequestDTO;
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
        throw new UnsupportedOperationException(this.getClass().getSimpleName() + "::" + GNSClassStackUtils.getEnclosingMethodObject(this) + " :: Not supported yet.");
        // return null;
    }

    /* ****** ****** ****** ****** */
    public <
            S extends BaseCommonEntity,
            T extends BaseResponseRequestDTO,
            W extends BaseRepository<? extends BaseCommonEntity, Integer>
            >
    List<T> fetchAllEntitiesAndConvertToDto(
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
    public <S extends BaseResponseRequestDTO,
            T extends BaseRepository<? extends BaseCommonEntity, Integer>,
            W extends BaseCommonEntity
            >
    Optional<S> findById(
            Integer id,
            Class<? extends BaseCommonEntity> inputEntityClass,
            Class<? extends BaseResponseRequestDTO> outputDTOClass,
            T inputRepositoryObject
    ) {
        @PositiveOrZero Optional<W> result = (Optional<W>) inputRepositoryObject.findById(id);
        Optional<S> responseDTO = Optional.empty();
        if (result.isPresent()) {
            W entityObject = result.get();
            responseDTO = Optional.ofNullable((S) BaseMapperEntity
                    .convertFromEntityToDTO(entityObject, (Class<? extends BaseResponseRequestDTO>) outputDTOClass));
        }
        return responseDTO;
    }


    public boolean existsById(Integer integer) {
        throw new UnsupportedOperationException(this.getClass().getSimpleName() + "::" + GNSClassStackUtils.getEnclosingMethodObject(this) + " :: Not supported yet.");
        // return false;
    }


    public Iterable<?> findAll() {
        throw new UnsupportedOperationException(this.getClass().getSimpleName() + "::" + GNSClassStackUtils.getEnclosingMethodObject(this) + " :: Not supported yet.");
        // return null;
    }


    public Iterable<?> findAllById(Iterable<Integer> integers) {

        throw new UnsupportedOperationException(this.getClass().getSimpleName() + "::" + GNSClassStackUtils.getEnclosingMethodObject(this) + " :: Not supported yet.");
    }


    public long count() {
        return 0;
    }


    public void deleteById(Integer integer) {
        throw new UnsupportedOperationException(this.getClass().getSimpleName() + "::" + GNSClassStackUtils.getEnclosingMethodObject(this) + " :: Not supported yet.");
    }


    public void delete(Object entity) {
        throw new UnsupportedOperationException(this.getClass().getSimpleName() + "::" + GNSClassStackUtils.getEnclosingMethodObject(this) + " :: Not supported yet.");
    }


    public void deleteAllById(Iterable<? extends Integer> integers) {
        throw new UnsupportedOperationException(this.getClass().getSimpleName() + "::" + GNSClassStackUtils.getEnclosingMethodObject(this) + " :: Not supported yet.");
    }


    public void deleteAll(Iterable<?> entities) {
        throw new UnsupportedOperationException(this.getClass().getSimpleName() + "::" + GNSClassStackUtils.getEnclosingMethodObject(this) + " :: Not supported yet.");
    }


    public void deleteAll() {
        throw new UnsupportedOperationException(this.getClass().getSimpleName() + "::" + GNSClassStackUtils.getEnclosingMethodObject(this) + " :: Not supported yet.");
    }

}
