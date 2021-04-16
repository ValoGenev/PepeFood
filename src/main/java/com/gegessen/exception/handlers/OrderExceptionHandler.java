package com.gegessen.exception.handlers;

import com.gegessen.controller.OrderController;
import com.gegessen.controller.ProductController;
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

@ControllerAdvice(assignableTypes = OrderController.class)
public class OrderExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderExceptionHandler.class);

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

    @ExceptionHandler(InvalidPurchaseQuantityException.class)
    public ResponseEntity<ErrorMessage> handleInvalidPurchaseQuantityException(InvalidPurchaseQuantityException exception) {
        LOGGER.error(BAD_REQUEST_MESSAGE,exception);

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
    public ResponseEntity<ErrorMessage> handleUserNotFoundException(UserNotFoundException exception) {
        LOGGER.error(NOT_FOUND_MESSAGE,exception);

        return status(NOT_FOUND).body(new ErrorMessage(exception.getMessage(), NOT_FOUND.value()));
    }

    @ExceptionHandler(RestaurantNotFoundException.class)
    public ResponseEntity<ErrorMessage> handleRestaurantNotFoundException(RestaurantNotFoundException exception) {
        LOGGER.error(NOT_FOUND_MESSAGE,exception);

        return status(NOT_FOUND).body(new ErrorMessage(exception.getMessage(), NOT_FOUND.value()));
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorMessage> handleProductNotFoundException(ProductNotFoundException exception) {
        LOGGER.error(NOT_FOUND_MESSAGE,exception);

        return status(NOT_FOUND).body(new ErrorMessage(exception.getMessage(), NOT_FOUND.value()));
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<ErrorMessage> handleOrderNotFoundException(OrderNotFoundException exception) {
        LOGGER.error(NOT_FOUND_MESSAGE,exception);

        return status(NOT_FOUND).body(new ErrorMessage(exception.getMessage(), NOT_FOUND.value()));
    }
}
