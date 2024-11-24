package pl.edu.pjatk.MPR_LAB2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pl.edu.pjatk.MPR_LAB2.controller.MyRestController;
import pl.edu.pjatk.MPR_LAB2.exception.*;
import pl.edu.pjatk.MPR_LAB2.model.Car;
import pl.edu.pjatk.MPR_LAB2.repository.CarRepository;
import pl.edu.pjatk.MPR_LAB2.services.CarService;


import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import java.util.*;
import static org.junit.jupiter.api.Assertions.*;


import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

public class CarServiceTest {

    @Mock
    private CarRepository repository;

    @Mock
    private pl.edu.pjatk.MPR_LAB2.services.StringUtilsService stringUtilsService;

    @InjectMocks
    private CarService carService;

    @InjectMocks
    private MyRestController myRestController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetCarByModelSuccess_DoesNotThrowsException(){
        Car car = new Car("testModel", "testColor");
        when(repository.findByModel("testModel")).thenReturn(Collections.singletonList(car));

        List<Car> cars = carService.getCarByModel("testModel");

        verify(repository, times(1)).findByModel("testModel");

        assertEquals("testModel", cars.getFirst().getModel());
        assertEquals("testColor", cars.getFirst().getColor());
        assertDoesNotThrow(() -> carService.getCarByModel("testModel"));
    }
    @Test
    public void testGetCarByModelNotFound_ThrowException() {
        when(repository.findByModel("testModel")).thenReturn(Collections.emptyList());
        assertThrows(CarNotFoundException.class, () -> carService.getCarByModel("testModel"));
        verify(repository, times(1)).findByModel("testModel");
    }
    @Test
    public void testGetCarByModelWrongDataInput_ThrowsException(){
        when(repository.findByModel("")).thenReturn(Collections.emptyList());
        assertThrows(CarWrongDataInputException.class, () -> carService.getCarByModel(""));
    }

    @Test
    public void testAddCarSuccess_DoesNotThrowsException() {
        Car car = new Car("testModel", "testColor");

        when(stringUtilsService.toProperCase(car.getModel())).thenReturn("Testmodel");
        when(stringUtilsService.toProperCase(car.getColor())).thenReturn("Testcolor");

        carService.add(car);

        verify(repository, times(1)).save(car);

        assertEquals("Testmodel", car.getModel());
        assertEquals("Testcolor", car.getColor());
        assertDoesNotThrow(() -> carService.add(car));
    }

    @Test
    public void testAddCarWrongDataBoth_ThrowException() {
        Car car = new Car("", "");
        assertThrows(CarWrongDataInputException.class, () -> carService.add(car));
    }
    @Test
    public void testAddCarWrongDataModel_ThrowsException() {
        Car car = new Car("RS6", "");
        assertThrows(CarWrongDataInputException.class, () -> carService.add(car));
    }
    @Test
    public void testAddCarWrongDataColor_ThrowsException() {
        Car car = new Car("RS6", "");
        assertThrows(CarWrongDataInputException.class, () -> carService.add(car));
    }

    @Test
    void testGetCarListSuccess_DoesNotThrowException() {
        Car car1 = new Car("carModel1", "carColor1");
        Car car2 = new Car("carModel2", "carColor2");

        when(repository.findAll()).thenReturn(Arrays.asList(car1, car2));
        when(stringUtilsService.toProperCase(car1.getModel())).thenReturn("Carmodel1");
        when(stringUtilsService.toProperCase(car1.getColor())).thenReturn("Carcolor1");
        when(stringUtilsService.toProperCase(car2.getModel())).thenReturn("Carmodel2");
        when(stringUtilsService.toProperCase(car2.getColor())).thenReturn("Carcolor2");

        var carList = carService.getCarList();

        assertEquals(2, carList.size());
        assertEquals("Carmodel1", carList.get(0).getModel());
        assertEquals("Carcolor1", carList.get(0).getColor());
        assertEquals("Carmodel2", carList.get(1).getModel());
        assertEquals("Carcolor2", carList.get(1).getColor());
        assertDoesNotThrow(()-> carService.getCarList());
    }
    @Test
    void testGetCarListEmpty_ThrowsException() {
        when(repository.findAll()).thenReturn(Collections.emptyList());
        assertThrows(CarNotFoundException.class, () -> carService.getCarList());
    }

    @Test
    void testCheckIfCarExists_ThrowsException() {
        Car car = new Car("rs6", "black");
        car.setIdentificator(792);
        when(repository.findByIdentificator(792)).thenReturn(Collections.singletonList(car));
        assertThrows(CarAlreadyExistsException.class, () -> carService.checkIfCarExists(car));
    }
    @Test
    void testCheckIfCarExists_DoesNotThrowException() {
        Car car = new Car("existingModel", "existingColor");
        car.setIdentificator(123);

        when(repository.findByIdentificator(123)).thenReturn(Collections.emptyList());

        assertDoesNotThrow(()-> carService.checkIfCarExists(car));
    }

    @Test
    void testGetCarSuccess_DoesNotThrowException(){
        Car car = new Car("carModel1", "carColor1");

        when(repository.findById(1L)).thenReturn(Optional.of(car));
        when(stringUtilsService.toProperCase(car.getModel())).thenReturn("Carmodel1");
        when(stringUtilsService.toProperCase(car.getColor())).thenReturn("Carcolor1");

        Car result = carService.getCar(1L);

        verify(repository, times(1)).findById(1L);
        assertEquals("Carmodel1", result.getModel());
        assertEquals("Carcolor1", result.getColor());
        assertDoesNotThrow(() -> carService.add(car));
    }
    @Test
    void testGetCarNotFound_ThrowsException() {
        Car car = new Car("carModel1", "carColor1");
        when(repository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(CarNotFoundException.class, () -> carService.getCar(1L));
    }

    @Test
    void testCarDeleteSuccess_DoesNotThrowException() {
        Car car = new Car("carModel1", "carColor1");
        when(repository.findById(1L)).thenReturn(Optional.of(car));
        carService.delete(1L);
        verify(repository, times(1)).findById(1L);
        verify(repository, times(1)).deleteById(1L);
        assertDoesNotThrow(( ) -> carService.delete(1L));
    }
    @Test
    void testCarDeleteFailure_ThrowsException() {
        when(repository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(CarNotFoundException.class, () -> carService.delete(1L));
    }

    @Test
    void testUpdateCarSuccess_DoesNotThrowException() {
        Long id = 1L;
        Car car = new Car("carModel", "carColor");
        when(repository.findById(1L)).thenReturn(Optional.of(car));
        when(stringUtilsService.toProperCase(car.getModel())).thenReturn("Carmodel");
        when(stringUtilsService.toProperCase(car.getColor())).thenReturn("Carcolor");
        when(repository.save(car)).thenReturn(car);
        assertDoesNotThrow( () -> carService.update(1L,car));
        verify(repository, times(1)).save(car);
    }
    @Test
    void testUpdateCarNotFound_ThrowsException() {
        when(repository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(CarNotFoundException.class, () -> carService.update(1L,new Car("carModel", "carColor")));
    }
    @Test
    void testUpdateCarAlreadyExists_ThrowsException() {
        Long id = 1L;
        Car car = new Car("rs6", "black");
        car.setIdentificator(792);
        when(repository.findById(1L)).thenReturn(Optional.of(car));
        when(repository.findByIdentificator(792)).thenReturn(Collections.singletonList(car));
        assertThrows(CarAlreadyExistsException.class, () -> carService.checkIfCarExists(car));
    }
    @Test
    void shouldReturnPdfContent() {
        Long carId = 1L;
        Car mockCar = new Car();
        mockCar.setId(carId);
        mockCar.setIdentificator(727);
        mockCar.setModel("w123");
        mockCar.setColor("Red");

        byte[] mockPdfContent = "PDF content".getBytes();

        when(carService.getCar(carId)).thenReturn(mockCar);
        when(carService.generareCarPdf(mockCar)).thenReturn(mockPdfContent);

        ResponseEntity<byte[]> response = myRestController.getPdf(carId);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertArrayEquals(mockPdfContent, response.getBody());
        assertEquals("application/pdf", response.getHeaders().getContentType().toString());

        verify(carService, times(1)).getCar(carId);
        verify(carService, times(1)).generareCarPdf(mockCar);
    }

}