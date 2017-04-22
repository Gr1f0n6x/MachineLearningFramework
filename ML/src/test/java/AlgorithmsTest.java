import Utilities.Algorithms;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by GrIfOn on 23.04.2017.
 */
public class AlgorithmsTest {

    @Test
    public void binPowerTest() {
        double result = Algorithms.binpow(5, 4);
        assertEquals(625, result, 0);
    }
}
