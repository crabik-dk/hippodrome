import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class HippodromeTest {


    @Test
    void whenHorsesListIsNull() {
        Exception exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Hippodrome(null));
        assertEquals("Horses cannot be null.", exception.getMessage());
    }
    @Test
    void whenHorsesListIsEmpty() {
        Exception exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Hippodrome(Collections.emptyList()));
        assertEquals("Horses cannot be empty.", exception.getMessage());
    }

    @Test
    void getHorsesReturnsCorrectList() {
        List<Horse> horses = IntStream.range(0, 30)
                .mapToObj(i -> new Horse("Horse" + i, i, i))
                .collect(Collectors.toList());

        Hippodrome hippodrome = new Hippodrome(horses);
        assertEquals(horses, hippodrome.getHorses());
    }

    @Test
    void callsMoveOnAllHorses() {
        List<Horse> horses = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            horses.add(spy(new Horse("Test" + i, 1)));
        }
        Hippodrome hippodrome = new Hippodrome(horses);
        hippodrome.move();

        horses.forEach(horse -> verify(horse).move());
    }

    @Test
    void integrationTestMoveSequenceAndGetWinner() {
        Horse horse1 = new Horse("SlowHorse", 1.0, 0);
        Horse horse2 = new Horse("FastHorse", 3.0, 0);

        Hippodrome hippodrome = new Hippodrome(List.of(horse1, horse2));

        double initialDistance1 = horse1.getDistance();
        double initialDistance2 = horse2.getDistance();

        for (int i = 0; i < 10; i++) {
            hippodrome.move();
        }
        assertTrue(horse1.getDistance() > initialDistance1);
        assertTrue(horse2.getDistance() > initialDistance2);
        assertTrue(horse2.getDistance() >= horse1.getDistance());

        Horse winner = hippodrome.getWinner();
        assertEquals(horse2, winner);
    }
    @Test
    void getWinnerReturnsHorseWithMaximumDistance() {
        Horse horse1 = new Horse("horse1", 1, 10);
        Horse horse2 = new Horse("horse2", 1, 20);
        Horse horse3 = new Horse("horse3", 1, 15);
        Hippodrome hippodrome = new Hippodrome(List.of(horse1, horse2, horse3));
        assertEquals(horse2, hippodrome.getWinner());
    }
    @Test
    void returnsFirstHorseWithSameDistance() {
        Horse horse1 = new Horse("FirstHorse", 1.0, 10.0);
        Horse horse2 = new Horse("SecondHorse", 2.0, 10.0);
        Horse horse3 = new Horse("ThirdHorse", 3.0, 10.0);

        Hippodrome hippodrome = new Hippodrome(List.of(horse1, horse2, horse3));
        assertEquals(horse1, hippodrome.getWinner());
    }
}