package br.com.example.apirestfulsb.exceptions;

import br.com.example.apirestfulsb.services.DataBindingViolationException;
import br.com.example.apirestfulsb.services.ObjectNotFoundException;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j(topic = "GLOBAL_EXCEPTION_HANDLER")
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  @Value("${server.error.include-exception}")
  private boolean printStackTrace;

  @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
      MethodArgumentNotValidException methodArgumentNotValidException,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest request
  ) {

    ErrorResponse errorResponse = new ErrorResponse(
        HttpStatus.UNPROCESSABLE_ENTITY.value(),
        "Validation error. Check 'errors' field for details"
    );

    for (FieldError fieldError : methodArgumentNotValidException.getBindingResult().getFieldErrors()) {
      errorResponse.addValidationError(fieldError.getField(), fieldError.getDefaultMessage());
    }

    return ResponseEntity.unprocessableEntity().body(errorResponse);
  }

  @ExceptionHandler(Exception.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ResponseEntity<Object> handleAllUncaughtException(Exception e, WebRequest request) {
    final String errorMessage = "Unknown error occurred";

    log.error(errorMessage, e);

    return buildErrorResponse(
        e,
        errorMessage,
        HttpStatus.INTERNAL_SERVER_ERROR,
        request
    );
  }

  @ExceptionHandler(DataIntegrityViolationException.class)
  @ResponseStatus(HttpStatus.CONFLICT)
  public ResponseEntity<Object> handleDataIntegrityViolationException(
      DataIntegrityViolationException integrityViolationException,
      WebRequest request
  ) {

    String errorMessage = integrityViolationException.getMostSpecificCause().getMessage();

    log.error("Failed to save entity with integrity problems: " + errorMessage, integrityViolationException.getMessage());

    return buildErrorResponse(
        integrityViolationException,
        errorMessage,
        HttpStatus.CONFLICT,
        request
    );
  }

  @ExceptionHandler(ConstraintViolationException.class)
  @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
  public ResponseEntity<Object> handleConstraintViolationException(
      ConstraintViolationException e,
      WebRequest request
  ) {

    log.error("Failed to validate element: " + e);

    return buildErrorResponse(
        e,
        HttpStatus.UNPROCESSABLE_ENTITY,
        request
    );
  }

  @ExceptionHandler(ObjectNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ResponseEntity<Object> handleObjectNotFoundException(
      ObjectNotFoundException objectNotFoundException,
      WebRequest request
  ) {

    log.error("Failed to find the request element", objectNotFoundException);

    return buildErrorResponse(
        objectNotFoundException,
        HttpStatus.NOT_FOUND,
        request
    );
  }

  @ExceptionHandler(DataBindingViolationException.class)
  @ResponseStatus(HttpStatus.CONFLICT)
  public ResponseEntity<Object> handleDataBindingViolationException(
      DataBindingViolationException dataBindingViolationException,
      WebRequest request
  ) {

    log.error("Failed to save entity with associated data", dataBindingViolationException);

    return buildErrorResponse(
        dataBindingViolationException,
        HttpStatus.CONFLICT,
        request
    );
  }

  private ResponseEntity<Object> buildErrorResponse(
      Exception e,
      HttpStatus status,
      WebRequest request
  ) {
    return buildErrorResponse(e, e.getMessage(), status, request);
  }

  private ResponseEntity<Object> buildErrorResponse(
      Exception e,
      String message,
      HttpStatus status,
      WebRequest request
  ) {
    ErrorResponse errorResponse = new ErrorResponse(status.value(), message);

    if (printStackTrace) {
      errorResponse.setStackTrace(ExceptionUtils.getStackTrace(e));
    }

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);

    return ResponseEntity.status(status).headers(headers).body(errorResponse);
  }


}