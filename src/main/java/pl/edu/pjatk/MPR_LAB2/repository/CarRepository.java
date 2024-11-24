package pl.edu.pjatk.MPR_LAB2.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.edu.pjatk.MPR_LAB2.model.Car;

import java.util.List;

@Repository
public interface CarRepository extends CrudRepository<Car, Long> {
    List<Car> findByModel(String model);
    List<Car> findByIdentificator(long identificator);
}