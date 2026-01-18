

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mockStatic;


class HorseTest {
    private Horse horse;

    @BeforeEach
    void setUp() {
        horse = new Horse("Horse", 5, 5);
    }

    @Test
    void whenFirstParamConstructorIsNull() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Horse(null, 5, 5));
        assertEquals("Name cannot be null.", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "\t", "\t ", "  ","\n \n", "\t \n\r\f"})
    void whenFirstParamConstructorIsBlank(String name) {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Horse(name, 5, 5));
        assertEquals("Name cannot be blank.", exception.getMessage());
    }

    @Test
    void whenSpeedParamConstructorIsNegative() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Horse("Horse", -5, 5));
        assertEquals("Speed cannot be negative.", exception.getMessage());
    }

    @Test
    void whenDistanceParamConstructorIsNegative() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Horse("Horse", 5, -5));
        assertEquals("Distance cannot be negative.", exception.getMessage());
    }

    @Test
    void returnsCorrectName() {
        String expected = "Horse";
        assertEquals(expected, horse.getName());
    }

    @Test
    void returnsCorrectSpeedValue() {
        double expected = 5;
        assertEquals(expected, horse.getSpeed());
    }

    @Test
    void returnsCorrectDistanceValue() {
        double expected = 5;
        assertEquals(expected, horse.getDistance());
    }

    @Test
    void returnZeroDistanceByDefault() {
        Horse horse = new Horse("TestHorse", 5.0);
        double expected = 0;
        assertEquals(expected, horse.getDistance());
    }

    @Test
    void moveUpdatesDistanceAccordingToFormula() {
        try (MockedStatic<Horse> mockedStatic = mockStatic(Horse.class)) {
            double randomValueMock = 0.5;
            mockedStatic.when(() -> Horse.getRandomDouble(0.2, 0.9)).thenReturn(randomValueMock);
            double initialDistance = horse.getDistance();
            double speed = horse.getSpeed();
            horse.move();
            mockedStatic.verify(() -> Horse.getRandomDouble(0.2, 0.9));
            double expectedDistance = initialDistance + speed * randomValueMock;
            assertEquals(expectedDistance, horse.getDistance());
        }
    }

    @Test
    void moveIncreasesDistanceConsistently() {
        Horse horse = new Horse("TestHorse", 2.0, 5.0);
        double initialDistance = horse.getDistance();
        double distanceBeforeMove = horse.getDistance();
        horse.move();
        assertTrue(horse.getDistance() > distanceBeforeMove);
        double distanceIncrease = horse.getDistance() - initialDistance;
        assertTrue(distanceIncrease >= 0.4 && distanceIncrease <= 1.8);
    }
}
