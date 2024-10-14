package pl.edu.pjatk.MPR_LAB2.controller;

import org.springframework.beans.factory.annotation.Autowired;
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
    public void addCar(@RequestBody Car car) {
        this.carService.add(car);
    }

    @DeleteMapping("car/{id}")
    public void deleteCar(@PathVariable Long id) {
        this.carService.delete(id);
    }

    @PatchMapping("car/{id}")
    public void patchUpdateCar(@PathVariable Long id, @RequestBody Car updatedCar) {
        this.carService.patchUpdate(id, updatedCar);
    }
    @GetMapping("car/all")
    public List<Car> getAllCars() {return this.carService.getAllCars();}

    @GetMapping("car/{id}")
    public Optional<Car> getCarById(@PathVariable Long id) {
        return this.carService.getCarByID(id);
    }

    @GetMapping("car/model/{model}")
    public List<Car> getByModel(@PathVariable String model) {
        return this.carService.getCarByModel(model);
    }

    @GetMapping("car/color/{color}")
    public List<Car> getByColor(@PathVariable String color) {
        return this.carService.getCarByBrand(color);
    }
}
