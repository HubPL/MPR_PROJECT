package pl.edu.pjatk.MPR_LAB2.services;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import pl.edu.pjatk.MPR_LAB2.model.Car;
import pl.edu.pjatk.MPR_LAB2.repository.CarRepository;

import java.util.List;
import java.util.Optional;

@Component
public class CarService {

    private CarRepository repository;

    public CarService(CarRepository repository) {
        this.repository = repository;

        this.repository.save(new Car("Mercedes", "zielony"));
        this.repository.save(new Car("BMW", "niebieski"));
        this.repository.save(new Car("Audi", "czarny"));
    }

    public List<Car> getCarByModel(String model) {
        return repository.findByModel(model);
    }
    public List<Car> getCarByBrand(String color) {
        return repository.findByColor(color);
    }
    public Optional<Car> getCarByID(Long id){
        return repository.findById(id);
    }
    public void add(@RequestBody Car car) {
       this.repository.save(car);
    }
    public void delete(Long id){
        this.repository.deleteById(id);
    }
    public List<Car> getAllCars(){
        return (List<Car>) this.repository.findAll();
    }
    public void patchUpdate(Long id, @RequestBody Car updatedCar) {
        Optional<Car> carOptional = repository.findById(id);
        if(carOptional.isPresent()) {
            Car car = carOptional.get();
            if (updatedCar.getModel() != null) {
                car.setModel(updatedCar.getModel());
            }
            if (updatedCar.getColor() != null) {
                car.setColor(updatedCar.getColor());
            }
            car.setId(updatedCar.getId());
            repository.save(car);
        } else {
            throw new RuntimeException("Car with ID " + id + " not found.");
        }
    }
}
