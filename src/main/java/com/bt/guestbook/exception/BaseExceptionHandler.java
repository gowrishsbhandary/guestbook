package com.bt.guestbook.exception;

import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.DataFormatException;

@ControllerAdvice
public class BaseExceptionHandler {

  private Logger logger = LoggerFactory.getLogger(BaseExceptionHandler.class);

  @ExceptionHandler(NotFoundException.class)
  public final ResponseEntity<Map<String, String>> handleNotFoundExceptions(
      NotFoundException ex, WebRequest request) {
    Map<String, String> errorMap = new HashMap<>();
    errorMap.put("error-code", "GB-001");
    errorMap.put("message", "No data found");
    logger.error(String.format("Errorcode=%s ExceptionMessage=%s", "GB-001", ex.getMessage()));
    return new ResponseEntity<Map<String, String>>(errorMap, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(IOException.class)
  public final ResponseEntity<Map<String, String>> handleIOExceptions(
      IOException ex, WebRequest request) {
    Map<String, String> errorMap = new HashMap<>();
    errorMap.put("error-code", "GB-002");
    errorMap.put("message", "Internal server error");
    logger.error(String.format("Errorcode=%s ExceptionMessage=%s", "GB-002", ex.getMessage()));
    return new ResponseEntity<Map<String, String>>(errorMap, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(DataFormatException.class)
  public final ResponseEntity<Map<String, String>> handleDataFormatExceptions(
      DataFormatException ex, WebRequest request) {
    Map<String, String> errorMap = new HashMap<>();
    errorMap.put("error-code", "GB-003");
    errorMap.put("message", "Internal server error");
    logger.error(String.format("Errorcode=%s ExceptionMessage=%s", "GB-003", ex.getMessage()));
    return new ResponseEntity<Map<String, String>>(errorMap, HttpStatus.BAD_REQUEST);
  }
}
