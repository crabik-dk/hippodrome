import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.concurrent.TimeUnit;

@Disabled
public class MainTest {

    @Test
    @Timeout(value = 23, unit = TimeUnit.SECONDS)
    void mainExecutionTimeTest() throws Exception {
        Main.main(new String[]{});
    }
}