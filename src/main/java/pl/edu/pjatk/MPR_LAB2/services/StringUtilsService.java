package pl.edu.pjatk.MPR_LAB2.services;

import org.springframework.stereotype.Component;
import pl.edu.pjatk.MPR_LAB2.exception.StringUtilsWrongInputException;


@Component
public class StringUtilsService {

    public String toUpperCase(String input) {
        if(input == null || input.isEmpty()) throw new StringUtilsWrongInputException();
        return input.toUpperCase();
    }

    public String toLowerCase(String input) {
        if(input == null || input.isEmpty()) throw new StringUtilsWrongInputException();
        return input.toLowerCase();
    }

    public String toProperCase(String input) {
        if(input == null || input.isEmpty()) throw new StringUtilsWrongInputException();
        return input.substring(0,1).toUpperCase() + input.substring(1).toLowerCase();
    }
}