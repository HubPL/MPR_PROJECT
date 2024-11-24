package pl.edu.pjatk.MPR_LAB2.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private int identificator;
    private String model;
    private String color;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Car(){}

    public Car(String model, String color) {
        this.model = model;
        this.color = color;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getIdentificator() {
        identificator = 0;
        for(int i = 0; i < model.length(); i++){
            identificator += model.toLowerCase().charAt(i);
        }
        for(int i = 0; i < color.length(); i++){
            identificator += color.toLowerCase().charAt(i);
        }
        return identificator;
    }

    public void setIdentificator(int identificator) {
        this.identificator = identificator;
    }

}