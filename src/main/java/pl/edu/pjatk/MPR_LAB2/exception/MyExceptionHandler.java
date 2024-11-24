package pl.edu.pjatk.MPR_LAB2.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class MyExceptionHandler extends RuntimeException {

    @ExceptionHandler(value={CarNotFoundException.class})
    public ResponseEntity<Object> handleNotFound(RuntimeException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value={CarAlreadyExistsException.class})
    public ResponseEntity<Object> handleAlreadyExists(CarAlreadyExistsException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value={CarWrongDataInputException.class})
    public ResponseEntity<Object> handleWrongData(CarWrongDataInputException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value={StringUtilsWrongInputException.class})
    public ResponseEntity<Object> handleWrongInput(StringUtilsWrongInputException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }
}
