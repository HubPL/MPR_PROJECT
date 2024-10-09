package pl.edu.pjatk.MPR_LAB2.services;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import pl.edu.pjatk.MPR_LAB2.model.Car;

import java.util.ArrayList;
import java.util.List;

@Component
public class CarService {
    List<Car> carList = new ArrayList<>();

    public CarService() {
        this.carList.add(new Car("Mercedes", "zielony"));
        this.carList.add(new Car("BMW", "niebieski"));
        this.carList.add(new Car("Audi", "czarny"));
    }

    public List<Car> getCarList() {
        return this.carList;
    }

    public void add(@RequestBody Car car) {
        this.carList.add(car);
    }

    public Car get(Integer id) {
        return this.carList.get(id);
    }

    public void delete(Integer id) {
        this.carList.remove(id);
    }

    public void update(Integer id,@RequestBody Car car) {
        this.carList.set(id, car);
    }

}
