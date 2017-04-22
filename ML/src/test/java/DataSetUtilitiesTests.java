import Utilities.DataSetUtilities;
import org.ejml.simple.SimpleMatrix;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by GrIfOn on 22.04.2017.
 */
public class DataSetUtilitiesTests {
    @Test
    public void removeRowTest() {
        SimpleMatrix A = new SimpleMatrix(new double[][] {
                {1},
                {2},
                {3},
                {4},
                {5},
        });

        SimpleMatrix B = DataSetUtilities.removeRow(A, 1);
        SimpleMatrix expected = new SimpleMatrix(new double[][] {
                {1},
                {3},
                {4},
                {5},
        });

        assertArrayEquals(expected.getMatrix().getData(), B.getMatrix().getData(), 0);
    }

    @Test
    public void removeRowsTest() {
        SimpleMatrix A = new SimpleMatrix(new double[][] {
                {1},
                {2},
                {3},
                {4},
                {5},
        });

        SimpleMatrix B = DataSetUtilities.removeRows(A, 1, 3);
        SimpleMatrix expected = new SimpleMatrix(new double[][] {
                {1},
                {5},
        });

        assertArrayEquals(expected.getMatrix().getData(), B.getMatrix().getData(), 0);
    }
}
