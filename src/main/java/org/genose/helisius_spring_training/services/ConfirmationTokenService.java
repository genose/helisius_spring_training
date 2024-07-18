package org.genose.helisius_spring_training.services;


import org.genose.helisius_spring_training.entities.ConfirmationTokenEntity;
import org.genose.helisius_spring_training.repositories.ConfirmationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ConfirmationTokenService {
    @Autowired
    private ConfirmationTokenRepository confirmationTokenRepository;

    public void saveConfirmationToken(ConfirmationTokenEntity token) {
        confirmationTokenRepository.save(token);
    }

    public Optional<ConfirmationTokenEntity> getToken(String token) {
        return confirmationTokenRepository.findByToken(token);
    }

    public void deleteToken(Long id) {
        confirmationTokenRepository.deleteById(id);
    }

}
