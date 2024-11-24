package pl.edu.pjatk.MPR_LAB2.exception;

public class CarAlreadyExistsException extends RuntimeException {
    public CarAlreadyExistsException() {
        super("Car already exists");
    }
}
