package pl.edu.pjatk.MPR_LAB2.exception;

public class CarNotFoundException extends RuntimeException {
    public CarNotFoundException() {
        super("Car not found!");
    }
}
