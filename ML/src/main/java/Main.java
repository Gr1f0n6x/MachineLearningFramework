import Data.DataSet;
import Data.DataSetUtilities;
import LearningAlgorithms.LinearRegression;
import org.ejml.simple.SimpleMatrix;

/**
 * Created by GrIfOn on 12.03.2017.
 */
public class Main {
    public static void main(String[] args) {

        double[][] data = new double[][]{
                {1, 1, 1},
                {2, 4, 4},
                {3, 9, 9},
                {4, 16, 16},
                {5, 25, 25},
        };

        SimpleMatrix matrix = new SimpleMatrix(data);
        matrix = DataSetUtilities.addColumnOfOnes(matrix);
        DataSet dataSet = new DataSet(matrix);
        dataSet.removeColumn(-1);
        dataSet.printMatrix();
//        LinearRegression linearRegression = new LinearRegression(0.001);
//        linearRegression.fit(DataSetUtilities.getTrainingSet(matrix, 0, 3), DataSetUtilities.getAnswersSet(matrix, 3), 200, 0);
//        linearRegression.plotCostFunctionHistory();

    }
}
