package pl.edu.pjatk.MPR_LAB2.services;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts.FontName;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import pl.edu.pjatk.MPR_LAB2.exception.CarAlreadyExistsException;
import pl.edu.pjatk.MPR_LAB2.exception.CarNotFoundException;
import pl.edu.pjatk.MPR_LAB2.exception.CarWrongDataInputException;
import pl.edu.pjatk.MPR_LAB2.model.Car;
import pl.edu.pjatk.MPR_LAB2.repository.CarRepository;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.apache.pdfbox.pdmodel.font.Standard14Fonts.FontName.HELVETICA;

@Component
public class CarService {

    private final StringUtilsService stringUtilsService;
    private final CarRepository repository;

    public CarService(CarRepository repository, StringUtilsService stringUtilsService) {
        this.repository = repository;
        this.stringUtilsService = stringUtilsService;

        add(new Car("w210", "black"));
        add(new Car("w220", "silver"));
        add(new Car("w124", "purple"));
    }

    public List<Car> getCarByModel(String model) {
        if(model == null || model.isEmpty()) throw new CarWrongDataInputException();
        List<Car> cars = repository.findByModel(model);
        if(cars.isEmpty()) throw new CarNotFoundException();
        return cars;
    }

    public List<Car> getCarList() {
        List<Car> cars = (List<Car>) this.repository.findAll();
        if(cars.isEmpty()) throw new CarNotFoundException();
        for (Car car : cars) {
            car.setModel(stringUtilsService.toProperCase(car.getModel()));
            car.setColor(stringUtilsService.toProperCase(car.getColor()));
        }
        return cars;
    }

    public void checkIfCarExists(Car car) {
        List<Car> carsBySum = this.repository.findByIdentificator(car.getIdentificator());
        if (!carsBySum.isEmpty()) {
            throw new CarAlreadyExistsException();
        }
    }

    public void add(Car car) {
        carWrongDataInputException(car);
        checkIfCarExists(car);
        car.setModel(stringUtilsService.toProperCase(car.getModel()));
        car.setColor(stringUtilsService.toProperCase(car.getColor()));
        this.repository.save(car);
    }

    public Car getCar(Long id) {
        Optional<Car> carOptional = this.repository.findById(id);
        if(carOptional.isEmpty()) throw new CarNotFoundException();

        Car car = carOptional.get();
        car.setModel(stringUtilsService.toProperCase(car.getModel()));
        car.setColor(stringUtilsService.toProperCase(car.getColor()));
        return car;
    }

    public void delete(Long id){
        Optional<Car> carOptional = this.repository.findById(id);
        if(carOptional.isEmpty()) throw new CarNotFoundException();

        this.repository.deleteById(id);
    }

    public void update(Long id, Car car){
        carWrongDataInputException(car);

        Optional<Car> carOptional = this.repository.findById(id);

        if(carOptional.isEmpty()) throw new CarNotFoundException();

        Car carToUpdate = carOptional.get();
        checkIfCarExists(car);

        carToUpdate.setModel(stringUtilsService.toProperCase(car.getModel()));
        carToUpdate.setColor(stringUtilsService.toProperCase(car.getColor()));
        carToUpdate.setIdentificator(car.getIdentificator());

        this.repository.save(carToUpdate);
    }

    public void carWrongDataInputException(Car car){
        if(car.getModel() == null || car.getModel().isEmpty()) throw new CarWrongDataInputException();
        if(car.getColor() == null || car.getColor().isEmpty()) throw new CarWrongDataInputException();
    }
    public byte[] generareCarPdf(Car car){
        try (PDDocument document = new PDDocument(); ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            PDPage page = new PDPage();
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.beginText();
                contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 12);
                contentStream.setLeading(14.5f);
                contentStream.newLineAtOffset(50, 700);

                contentStream.showText("Car Information:");
                contentStream.newLine();
                contentStream.showText("ID: " + car.getId());
                contentStream.newLine();
                contentStream.showText("Identificator: " + car.getIdentificator());
                contentStream.newLine();
                contentStream.showText("Model: " + car.getModel());
                contentStream.newLine();
                contentStream.showText("Color: " + car.getColor());

                contentStream.endText();
            }

            document.save(outputStream);
            return outputStream.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException("Failed to generate PDF", e);
        }
    }
}