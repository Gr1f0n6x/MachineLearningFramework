import Core.kNN.KNN;
import Utilities.DataSetUtilities;
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

                {3, 3, 1},
                {3, 2, 1},
                {3, 1, 1},
                {4, 3, 0},
                {4, 2, 0},
                {4, 1, 0},

                {5, 1, 1},
                {5, 2, 1},
                {5, 3, 1},
                {6, 1, 1},
                {6, 2, 1},
                {6, 3, 1},

                {7, 3, 2},
                {7, 2, 2},
                {7, 1, 2},
                {8, 3, 1},
                {8, 2, 1},
                {8, 1, 1},

                {9, 1, 2},
                {9, 2, 2},
                {9, 3, 2},
                {10, 1, 2},
                {10, 2, 2},
                {10, 3, 2},
        };

        SimpleMatrix dataset = new SimpleMatrix(data);

        KNN knn = new KNN(3);

        assertEquals(9, knn.LOO(DataSetUtilities.getTrainingSet(dataset, 0, 1), DataSetUtilities.getAnswersSet(dataset, 2), 34, 0));
    }
}
