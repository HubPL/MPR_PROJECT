package pl.edu.pjatk.MPR_LAB2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.pjatk.MPR_LAB2.model.Car;
import pl.edu.pjatk.MPR_LAB2.services.CarService;

import java.util.List;
import java.util.Optional;

@RestController
public class MyRestController {
    private final CarService carService;

    @Autowired
    public MyRestController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping("car/model/{model}")
    public ResponseEntity<List<Car>> getByModel(@PathVariable String model) {
        return new ResponseEntity<>(this.carService.getCarByModel(model), HttpStatus.OK);
    }

    @GetMapping("car/all")  // <-- endpoint
    public ResponseEntity<List<Car>> getAll(){
        return new ResponseEntity<>(this.carService.getCarList(), HttpStatus.OK);
    }

    @GetMapping("car/{id}")
    public ResponseEntity<Car> get(@PathVariable Long id){
        return new ResponseEntity<>(this.carService.getCar(id), HttpStatus.OK);
    }

    @PostMapping("car")
    public ResponseEntity<Void> addCar(@RequestBody Car car){
        this.carService.add(car);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("car/{id}")
    public ResponseEntity<Void> deleteCar(@PathVariable Long id){
        this.carService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("car/{id}")
    public ResponseEntity<Void> updateCar(@PathVariable Long id,  @RequestBody Car car){
        this.carService.update(id, car);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("car/{id}/pdf")
    public ResponseEntity<byte[]> getPdf(@PathVariable Long id) {
        Car car = carService.getCar(id);
        byte[] pdfContent = carService.generareCarPdf(car);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("filename", "car_" + id + ".pdf");

        return new ResponseEntity<>(pdfContent, headers, HttpStatus.OK);
    }

}