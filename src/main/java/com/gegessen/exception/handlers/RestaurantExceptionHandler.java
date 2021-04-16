package com.gegessen.exception.handlers;

import com.gegessen.controller.RestaurantController;
import com.gegessen.controller.UserController;
import com.gegessen.exception.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.stream.Collectors;

import static com.gegessen.util.Constants.*;
import static com.gegessen.util.Constants.NOT_FOUND_MESSAGE;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.ResponseEntity.status;

@ControllerAdvice(assignableTypes = RestaurantController.class)
public class RestaurantExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserExceptionHandler.class);

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<ErrorMessage> handleServiceException(ServiceException exception){
        LOGGER.error(DATABASE_ERROR_MESSAGE,exception);

        return status(INTERNAL_SERVER_ERROR).body(new ErrorMessage(exception.getMessage(),INTERNAL_SERVER_ERROR.value()));
    }

    @ExceptionHandler(InvalidInputException.class)
    public ResponseEntity<ErrorMessage> handleInvalidInputException(InvalidInputException exception) {
        LOGGER.error(BAD_REQUEST_MESSAGE,exception);

        return status(BAD_REQUEST).body(new ErrorMessage(exception.getMessage(), BAD_REQUEST.value()));
    }

    @ExceptionHandler(AlreadyOwningRestaurantException.class)
    public ResponseEntity<ErrorMessage> handleAlreadyOwningRestaurantException(AlreadyOwningRestaurantException exception) {
        LOGGER.error(USER_ALREADY_OWNING_RESTAURANT,exception);

        return status(BAD_REQUEST).body(new ErrorMessage(exception.getMessage(), BAD_REQUEST.value()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessage> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        LOGGER.error(BAD_REQUEST_MESSAGE,exception);

        String errors = exception.getBindingResult().getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(","));

        return status(BAD_REQUEST).body(new ErrorMessage(errors, BAD_REQUEST.value()));
    }

    @ExceptionHandler(AlreadyExistingResourceException.class)
    public ResponseEntity<ErrorMessage> handleAlreadyExistingResourceException(AlreadyExistingResourceException exception) {
        LOGGER.error(EXISTING_RESOURCE_MESSAGE,exception);

        return status(CONFLICT).body(new ErrorMessage(exception.getMessage(), CONFLICT.value()));
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ErrorMessage> handleConflictException(ConflictException exception) {
        LOGGER.error(EXISTING_RESOURCE_MESSAGE,exception);

        return status(CONFLICT).body(new ErrorMessage(exception.getMessage(), CONFLICT.value()));
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorMessage> handleResourceNotFoundException(UserNotFoundException exception) {
        LOGGER.error(NOT_FOUND_MESSAGE,exception);

        return status(NOT_FOUND).body(new ErrorMessage(exception.getMessage(), NOT_FOUND.value()));
    }


    @ExceptionHandler(RestaurantNotFoundException.class)
    public ResponseEntity<ErrorMessage> handleRestaurantNotFoundException(RestaurantNotFoundException exception) {
        LOGGER.error(NOT_FOUND_MESSAGE,exception);

        return status(NOT_FOUND).body(new ErrorMessage(exception.getMessage(), NOT_FOUND.value()));
    }


}
