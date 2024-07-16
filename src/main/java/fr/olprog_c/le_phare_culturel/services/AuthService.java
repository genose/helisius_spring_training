package fr.olprog_c.le_phare_culturel.services;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import fr.olprog_c.le_phare_culturel.dtos.AuthRegisterPostDTO;
import fr.olprog_c.le_phare_culturel.dtos.mapper.AuthDTOMapper;
import fr.olprog_c.le_phare_culturel.entities.UserEntity;
import fr.olprog_c.le_phare_culturel.exceptions.CustomException;
import fr.olprog_c.le_phare_culturel.repositories.UserRepository;

@Service
public class AuthService {

  private final AuthDTOMapper authDTOMapper;
  private final UserRepository userRepository;

  public AuthService(AuthDTOMapper authDTOMapper, UserRepository userRepository) {
    this.authDTOMapper = authDTOMapper;
    this.userRepository = userRepository;
  }

  public void register(AuthRegisterPostDTO authRegisterPostDTO) throws CustomException {
    try {
      UserEntity user = authDTOMapper.registerDtoToEntity(authRegisterPostDTO);
      this.userRepository.save(user);
      System.out.println(user);

    } catch (Exception e) {
      throw new CustomException(HttpStatus.BAD_REQUEST, e.getMessage());
    }
  }

}
