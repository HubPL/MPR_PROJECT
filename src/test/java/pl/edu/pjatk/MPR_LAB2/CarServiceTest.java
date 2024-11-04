package pl.edu.pjatk.MPR_LAB2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pl.edu.pjatk.MPR_LAB2.model.Car;
import pl.edu.pjatk.MPR_LAB2.repository.CarRepository;
import pl.edu.pjatk.MPR_LAB2.services.CarService;
import pl.edu.pjatk.MPR_LAB2.services.StringUtilsService;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CarServiceTest {

    @Mock
    private CarRepository carRepository;

    @Mock
    private StringUtilsService stringUtilsService;

    @InjectMocks
    private CarService carService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddCar() {
        Car car = new Car("mercedes", "zielony");
        when(stringUtilsService.toUpperCase(car.getModel())).thenReturn("MERCEDES");
        when(stringUtilsService.toUpperCase(car.getColor())).thenReturn("ZIELONY");

        carService.add(car);

        verify(carRepository, times(1)).save(any(Car.class));
    }

    @Test
    public void testGetAllCars() {
        Car car1 = new Car("mercedes", "zielony");
        Car car2 = new Car("bmw", "niebieski");
        when(carRepository.findAll()).thenReturn(Arrays.asList(car1, car2));

        when(stringUtilsService.capitalize(car1.getModel())).thenReturn("Mercedes");
        when(stringUtilsService.capitalize(car2.getModel())).thenReturn("Bmw");

        assertEquals(2, carService.getAllCars().size());
        assertEquals("Mercedes", carService.getAllCars().get(0).getModel());
        assertEquals("Bmw", carService.getAllCars().get(1).getModel());
    }

    @Test
    public void testGetCarByID() {
        Car car = new Car("audi", "czarny");
        car.setId(1L);
        when(carRepository.findById(1L)).thenReturn(Optional.of(car));
        when(stringUtilsService.capitalize(car.getModel())).thenReturn("Audi");

        Car foundCar = carService.getCarByID(1L);
    }

    @Test
    public void testPatchUpdate() {
        Car existingCar = new Car("audi", "czarny");
        existingCar.setId(1L);

        when(carRepository.findById(1L)).thenReturn(Optional.of(existingCar));

        Car updatedCar = new Car("mercedes", null);
        when(stringUtilsService.capitalize(updatedCar.getModel())).thenReturn("Mercedes");

        carService.patchUpdate(1L, updatedCar);

        assertEquals("Mercedes", existingCar.getModel());
        verify(carRepository, times(1)).save(existingCar);
    }
}