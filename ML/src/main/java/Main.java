import Data.DataNormalization;
import Data.DataSetUtilities;
import LearningAlgorithms.KNN;
import Plot.XYClassScatterPlot;
import org.ejml.simple.SimpleMatrix;

/**
 * Created by GrIfOn on 12.03.2017.
 */
public class Main {
    public static void main(String[] args) {

//        double[][] data = new double[][] {
//                {1, 4, 0},
//                {3, 2, 0},
//                {4, 5, 0},
//                {4, 2, 0},
//                {2, 1, 0},
//
//                {6, 10, 1},
//                {9, 8, 1},
//                {6, 7, 1},
//                {7, 9, 1},
//                {9, 6, 1},
//
//                {11, 2, 2},
//                {15, 4, 2},
//                {14, 3.2, 2},
//                {12, 5, 2},
//                {15, 1, 2},
//        };

        double[][] data = new double[][]{
                {1, 1, 1},
                {2, 2, 2},
                {3, 3, 3},
                {4, 4, 4},
                {5, 5, 5},
        };

        SimpleMatrix dataSet = new SimpleMatrix(data);

//        XYClassScatterPlot xyClassScatterPlot = new XYClassScatterPlot("data", dataSet);
//        xyClassScatterPlot.plot();

        dataSet.print();

        //SimpleMatrix[] matrixes = DataSetUtilities.getCrossValidationAndTrainSets(DataSetUtilities.getTrainingSet(dataSet, 0, 1), DataSetUtilities.getAnswersSet(dataSet, 2), 0.5, true);
        SimpleMatrix[] matrixes = DataSetUtilities.getCrossValidationAndTrainSets(dataSet, 0.5, true);
        for(SimpleMatrix matrix : matrixes) {
            matrix.print();
        }
    }
}
