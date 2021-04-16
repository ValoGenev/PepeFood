package com.gegessen.service.user;

import com.gegessen.dto.user.UserAllPropertiesDto;
import com.gegessen.entity.UserEntity;
import com.gegessen.exception.*;
import com.gegessen.model.UserType;
import com.gegessen.repository.IUserRepository;
import com.gegessen.service.email.IMailService;
import org.hibernate.service.spi.ServiceException;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.gegessen.model.UserRole.USER;
import static com.gegessen.util.Constants.*;
import static com.gegessen.util.Constants.UPDATE_USER_BY_EMAIL_MESSAGE;
import static java.lang.String.format;
import static java.util.Objects.isNull;


public class UserService implements IUserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    private final IUserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private IMailService mailService;

    @Autowired
    public UserService(IUserRepository userRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Set<UserAllPropertiesDto> findAll() {
        LOGGER.info(GET_ALL_USERS_MESSAGE);

        try {
            return userRepository
                    .findAll()
                    .stream()
                    .map(user -> modelMapper.map(user, UserAllPropertiesDto.class))
                    .collect(Collectors.toSet());
        } catch (DataAccessException e) {
            LOGGER.error(DATABASE_ERROR_MESSAGE);
            throw new ServiceException(DATABASE_ERROR_MESSAGE);
        }
    }

    @Override
    public UserAllPropertiesDto findOne(String id) {
        LOGGER.info(format(FIND_USER_BY_EMAIL_MESSAGE, id));

        return modelMapper.map(findUser(id), UserAllPropertiesDto.class);
    }

    @Override
    public void delete(String id) {
        LOGGER.info(format(DELETE_USER_BY_EMAIL_MESSAGE, id));

        try {
            userRepository.findByEmail(id).ifPresent(userRepository::delete);
        } catch (DataIntegrityViolationException e) {
            LOGGER.error(CONFLICT_DELETE_MESSAGE);
            throw new ConflictException(CONFLICT_DELETE_MESSAGE);
        } catch (DataAccessException e) {
            LOGGER.error(DATABASE_ERROR_MESSAGE);
            throw new ServiceException(DATABASE_ERROR_MESSAGE);
        }
    }

    @Override
    public UserAllPropertiesDto create(UserAllPropertiesDto user) {
        LOGGER.info(format(CREATE_USER_MESSAGE, user.getEmail()));

        assertNotExistingEmail(user.getEmail());

        UserEntity userToBeCreated = modelMapper.map(user,UserEntity.class);

        userToBeCreated.setOrders(new HashSet<>());
        userToBeCreated.setRestaurant(null);
        userToBeCreated.setValid(true);

        return modelMapper.map(createUser(userToBeCreated), UserAllPropertiesDto.class);
    }

    @Override
    public UserAllPropertiesDto update(UserAllPropertiesDto user, String id) {
        LOGGER.info(format(UPDATE_USER_BY_EMAIL_MESSAGE, user.getId()));

        UserEntity userInDb = findUser(id);

        assertEqualEmail(user.getEmail(),userInDb.getEmail());

        user.setId(userInDb.getId());

        UserEntity userToBeUpdated = modelMapper.map(user,UserEntity.class);

        userToBeUpdated.setOrders(userInDb.getOrders());
        userToBeUpdated.setRestaurant(userInDb.getRestaurant());

        return modelMapper.map(createUser(userToBeUpdated),UserAllPropertiesDto.class);
    }

    @Override
    public void sendVerificationEmail(String email) {

        String newEmailVerificationToken = UUID.randomUUID().toString();

        UserEntity user = userRepository.findByEmail(email).orElse(null);

       if(user==null){
           UserEntity userEntity = new UserEntity();

           userEntity.setName(null);
           userEntity.setRole(USER);
           userEntity.setUserType(UserType.EMAIL);
           userEntity.setRestaurant(null);
           userEntity.setOrders(new HashSet<>());
           userEntity.setEmail(email);
           userEntity.setFacebookClientId(null);
           userEntity.setPicUrl(null);
           userEntity.setValid(false);
           userEntity.setEmailVerificationToken(newEmailVerificationToken);

           user =  userRepository.save(userEntity);

       }else {
           user.setEmailVerificationToken(newEmailVerificationToken);
           user = userRepository.save(user);
       }

       mailService.sentVerificationEmail(user);

    }

    private UserEntity createUser(UserEntity user) {

        try {
            return userRepository.save(user);

        } catch (DataIntegrityViolationException e) {

            LOGGER.error(CONFLICT_CREATE_MESSAGE);
            throw new AlreadyExistingResourceException(EXISTING_RESOURCE_MESSAGE);
        } catch (DataAccessException e) {
            LOGGER.error(DATABASE_ERROR_MESSAGE, e);
            throw new ServiceException(DATABASE_ERROR_MESSAGE);
        }
    }


    private UserEntity findUser(String id) {
        try {
            return userRepository
                    .findById(id)
                    .orElseThrow(() -> new UserNotFoundException(format(USER_NOT_FOUND_MESSAGE, id)));
        } catch (DataAccessException e) {
            LOGGER.error(DATABASE_ERROR_MESSAGE);
            throw new ServiceException(DATABASE_ERROR_MESSAGE);
        }
    }


    private void assertNotExistingEmail(String email){
        if(userRepository.checkIfEmailExists(email)){
            throw new AlreadyExistingEmailException(format(EXISTING_EMAIL_MESSAGE, email));
        }
    }

    private void assertEqualEmail(String email1, String email2) {
        if (!email1.equals(email2)) {
            throw new EmailNotEqualException(EMAIL_NOT_EQUAL_MESSAGE);
        }
    }
}
