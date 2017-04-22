import Core.KNN;
import Data.DataSetUtilities;
import org.ejml.simple.SimpleMatrix;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by GrIfOn on 22.04.2017.
 */
public class kNNTests {

    @Test
    public void looTest() {
        double[][] data = new double[][] {
                {1, 1, 0},
                {1, 2, 0},
                {1, 3, 0},
                {2, 1, 0},
                {2, 2, 0},
                {2, 3, 0},

                {5, 5, 1},
                {5, 6, 1},
                {5, 7, 1},
                {6, 5, 1},
                {6, 6, 1},
                {6, 7, 1},

                {8, 9, 2},
                {8, 10, 2},
                {8, 11, 2},
                {9, 9, 2},
                {9, 10, 2},
                {9, 11, 2},
        };

        SimpleMatrix dataset = new SimpleMatrix(data);

        KNN knn = new KNN(3);

        assertEquals(3, knn.LOO(DataSetUtilities.getTrainingSet(dataset, 0, 1), DataSetUtilities.getAnswersSet(dataset, 2), 10, 1));
    }
}
