package pl.edu.pjatk.MPR_LAB2;

import org.junit.jupiter.api.Test;
import pl.edu.pjatk.MPR_LAB2.services.StringUtilsService;

import static org.junit.jupiter.api.Assertions.*;

public class StringUtilsServiceTest {

    private final StringUtilsService stringUtilsService = new StringUtilsService();

    @Test
    public void testToUpperCase() {
        assertEquals("BMW", stringUtilsService.toUpperCase("bmw"));
        assertEquals("MERCEDES", stringUtilsService.toUpperCase("Mercedes"));
    }

    @Test
    public void testCapitalize() {
        assertEquals("Bmw", stringUtilsService.capitalize("bmw"));
        assertEquals("Mercedes", stringUtilsService.capitalize("MERCEDES"));
    }
}