package br.com.mfsdevsystems.dscatalog.resources.exceptions;

import java.time.Instant;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import br.com.mfsdevsystems.dscatalog.services.exception.DatabaseException;
import br.com.mfsdevsystems.dscatalog.services.exception.ResourceNotFoundException;

@ControllerAdvice
public class ResourceExceptionHandler {
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<StandardError> entityNotFound(ResourceNotFoundException e, HttpServletRequest request){
		HttpStatus httpStatus = HttpStatus.NOT_FOUND ;
		
		StandardError err = new StandardError();
		err.setTimestamp( Instant.now() );
		err.setStatus(httpStatus.value());
		err.setError("Resource not Found");
		err.setMessage(e.getMessage());
		err.setPath(request.getRequestURI());
		
		return ResponseEntity.status(httpStatus).body(err);
		
	}
	
	@ExceptionHandler(DatabaseException.class)
	public ResponseEntity<StandardError> databaseError(DatabaseException e, HttpServletRequest request){
		HttpStatus httpStatus = HttpStatus.BAD_REQUEST ;
		
		StandardError err = new StandardError();
		
		err.setTimestamp( Instant.now() );
		err.setStatus(httpStatus.value());
		err.setError("Database Exception");
		err.setMessage(e.getMessage());
		err.setPath(request.getRequestURI());
		
		return ResponseEntity.status(httpStatus).body(err);
		
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ValidationError> validationError(MethodArgumentNotValidException e, HttpServletRequest request){
		HttpStatus httpStatus = HttpStatus.UNPROCESSABLE_ENTITY ;
		
		ValidationError err = new ValidationError();
		
		err.setTimestamp( Instant.now() );
		err.setStatus( httpStatus.value());
		err.setError( "Validation Exception");
		err.setMessage( e.getMessage() );
		err.setPath(request.getRequestURI());
		
		for (FieldError f : e.getBindingResult().getFieldErrors()) {
			
			err.addError(f.getField(), f.getDefaultMessage());
		}
		
		return ResponseEntity.status( httpStatus ).body(err);
		
	}
}
