package _5bits.organik.platform.conservation.domain.model.valueobjects;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TemperatureRangeTest {

    @Test
    void includesValuesBetweenItsLimits() {
        var range = new TemperatureRange(new Temperature(2.0), new Temperature(8.0));

        assertTrue(range.includes(new Temperature(2.0)));
        assertTrue(range.includes(new Temperature(8.0)));
        assertFalse(range.includes(new Temperature(8.1)));
    }

    @Test
    void rejectsMinimumGreaterThanMaximum() {
        var exception = assertThrows(IllegalArgumentException.class,
                () -> new TemperatureRange(new Temperature(9.0), new Temperature(8.0)));
        assertEquals("Minimum temperature cannot be greater than maximum temperature", exception.getMessage());
    }
}
