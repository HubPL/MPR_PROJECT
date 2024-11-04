package pl.edu.pjatk.MPR_LAB2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.pjatk.MPR_LAB2.model.Car;
import pl.edu.pjatk.MPR_LAB2.services.CarService;

import java.util.List;
import java.util.Optional;

@RestController
public class MyRestController {
    private CarService carService;

    @Autowired
    public MyRestController(CarService carService) {
        this.carService = carService;
    }

    @PostMapping("car")
    public ResponseEntity<Void> addCar(@RequestBody Car car) {
        this.carService.add(car);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("car/{id}")
    public ResponseEntity<Void> deleteCar(@PathVariable Long id) {
        this.carService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("car/{id}")
    public ResponseEntity<Void> patchUpdateCar(@PathVariable Long id, @RequestBody Car updatedCar) {
        this.carService.patchUpdate(id, updatedCar);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("car/all")
    public ResponseEntity<List<Car>> getAllCars() {
        return new ResponseEntity<>(this.carService.getAllCars(), HttpStatus.OK);
    }

    @GetMapping("car/{id}")
    public ResponseEntity<Car> getCarById(@PathVariable Long id) {
        return new ResponseEntity<>(this.carService.getCarByID(id), HttpStatus.OK);
    }

    @GetMapping("car/model/{model}")
    public ResponseEntity<List<Car>> getByModel(@PathVariable String model) {
       return new ResponseEntity<>(this.carService.getCarByModel(model), HttpStatus.OK);
    }

    @GetMapping("car/color/{color}")
    public ResponseEntity<List<Car>> getByColor(@PathVariable String color) {
        return new ResponseEntity<>(this.carService.getCarByColor(color), HttpStatus.OK);
    }
}