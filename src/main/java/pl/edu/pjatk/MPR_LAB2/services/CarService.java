package pl.edu.pjatk.MPR_LAB2.services;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import pl.edu.pjatk.MPR_LAB2.exception.CarNotFoundException;
import pl.edu.pjatk.MPR_LAB2.model.Car;
import pl.edu.pjatk.MPR_LAB2.repository.CarRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class CarService {

    private final CarRepository repository;
    private final StringUtilsService stringUtilsService;

    public CarService(CarRepository repository, StringUtilsService stringUtilsService) {
        this.repository = repository;
        this.stringUtilsService = stringUtilsService;

        this.repository.save(new Car("Mercedes", "zielony"));
        this.repository.save(new Car("BMW", "niebieski"));
        this.repository.save(new Car("Audi", "czarny"));
    }

    public List<Car> getCarByModel(String model) {
        if(model == null || model.isEmpty()) throw new CarNotFoundException();
        return repository.findByModel(model);

    }

    public List<Car> getCarByColor(String color) {
        if(color == null || color.isEmpty()) throw new CarNotFoundException();
        return this.repository.findByColor(color);
    }

    public Car getCarByID(Long id) {
        Optional<Car> car = this.repository.findById(id);

        if(car.isEmpty()) {
            throw new CarNotFoundException();
        }
        return car.get();
    }

    public void add(@RequestBody Car car) {
        car.setModel(stringUtilsService.toUpperCase(car.getModel()));
        car.setColor(stringUtilsService.toUpperCase(car.getColor()));
        this.repository.save(car);
    }

    public void delete(Long id) {
        Optional<Car> car = this.repository.findById(id);

        if(car.isEmpty()) {
            throw new CarNotFoundException();
        }
        this.repository.deleteById(id);
    }

    public List<Car> getAllCars() {
        return ((List<Car>) this.repository.findAll()).stream()
                .map(this::capitalizeCar)
                .collect(Collectors.toList());
    }

    public void patchUpdate(Long id, @RequestBody Car updatedCar) {
        Optional<Car> carOptional = repository.findById(id);
        if (carOptional.isPresent()) {
            Car car = carOptional.get();
            if (updatedCar.getModel() != null) {
                car.setModel(stringUtilsService.capitalize(updatedCar.getModel()));
            }
            if (updatedCar.getColor() != null) {
                car.setColor(stringUtilsService.capitalize(updatedCar.getColor()));
            }
            car.setId(updatedCar.getId());
            repository.save(car);
        } else {
            throw new CarNotFoundException();
        }
    }

    private Car capitalizeCar(Car car) {
        car.setModel(stringUtilsService.capitalize(car.getModel()));
        car.setColor(stringUtilsService.capitalize(car.getColor()));
        return car;
    }
}