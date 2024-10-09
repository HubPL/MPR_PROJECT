package pl.edu.pjatk.MPR_LAB2.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.edu.pjatk.MPR_LAB2.model.Car;
import pl.edu.pjatk.MPR_LAB2.services.CarService;

import java.util.List;

    @RestController
    public class MyRestController {
    private CarService carService;

    @Autowired
    public MyRestController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping("car/all")
    public List<Car> getAll() {
        return this.carService.getCarList();
    }

    @GetMapping("car/{id}")
    public Car get(@PathVariable Integer id) {
        return this.carService.get(id);
    }

    @PostMapping("car")
    public void addCar(@RequestBody Car car) {
        this.carService.add(car);
    }
//NIE DZIALA DELETE!!!!
    @DeleteMapping("car/{id}")
    public void delete(@PathVariable Integer id) {
        this.carService.delete(id);
    }

    @PutMapping("car/{id}")
        public void updateCar(@PathVariable Integer id, @RequestBody Car car) {
        this.carService.update(id, car);
    }

}
