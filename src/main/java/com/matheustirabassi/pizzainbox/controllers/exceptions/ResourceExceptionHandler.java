package com.matheustirabassi.pizzainbox.controllers.exceptions;

import com.matheustirabassi.pizzainbox.services.exceptions.AuthorizationException;
import com.matheustirabassi.pizzainbox.services.exceptions.DataIntegrityException;
import com.matheustirabassi.pizzainbox.services.exceptions.ObjectNotFoundException;
import java.sql.SQLIntegrityConstraintViolationException;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Handler para para manipulação de erros.
 */
@ControllerAdvice
public class ResourceExceptionHandler {

  /**
   * Esse método tratatá as exceções desse tipo. A anotação é que define isso. A assinatura desse
   * método é padrão.
   *
   * @ExceptionHandler Define que o método será um tratador de exceções do tipo do parâmetro da
   * anotação.
   */
  @ExceptionHandler(ObjectNotFoundException.class)
  public ResponseEntity<StandardError> objectNotFound(ObjectNotFoundException e,
      HttpServletRequest request) {
    StandardError err =
        new StandardError(HttpStatus.NOT_FOUND.value(), e.getMessage(), System.currentTimeMillis());

    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
  }

  @ExceptionHandler(DataIntegrityException.class)
  public ResponseEntity<StandardError> dataIntegrity(DataIntegrityException e,
      HttpServletRequest request) {
    StandardError err = new StandardError(HttpStatus.BAD_REQUEST.value(), e.getMessage(),
        System.currentTimeMillis());

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<StandardError> validation(MethodArgumentNotValidException e,
      HttpServletRequest request) {
    ValidationError err = new ValidationError(HttpStatus.BAD_REQUEST.value(), "Erro de validação",
        System.currentTimeMillis());

    for (FieldError x : e.getBindingResult().getFieldErrors()) {
      err.addError(x.getField(), x.getDefaultMessage());
    }
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
  }

  @ExceptionHandler(AuthorizationException.class)
  public ResponseEntity<StandardError> authorization(AuthorizationException e,
      HttpServletRequest request) {
    StandardError err =
        new StandardError(HttpStatus.FORBIDDEN.value(), e.getMessage(), System.currentTimeMillis());

    // retorna o erro HTTP correspondente a "acesso negado"
    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(err);
  }

  @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
  public ResponseEntity<StandardError> duplicateField(SQLIntegrityConstraintViolationException e,
      HttpServletRequest request) {
    StandardError err =
        new StandardError(HttpStatus.BAD_REQUEST.value(), e.getMessage(),
            System.currentTimeMillis());

    // retorna o erro HTTP correspondente a "entidade duplicada"
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
  }

}