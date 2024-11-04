package pl.edu.pjatk.MPR_LAB2.services;

import org.springframework.stereotype.Service;

@Service
public class StringUtilsService {

    public String toUpperCase(String input) {
        return input != null ? input.toUpperCase() : null;
    }

    public String capitalize(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }
        return input.substring(0, 1).toUpperCase() + input.substring(1).toLowerCase();
    }
}