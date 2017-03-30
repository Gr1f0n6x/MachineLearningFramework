package Examples;

import Data.DataSetUtilities;
import LearningAlgorithms.KNN;
import Plot.XYClassScatterPlot;
import org.ejml.simple.SimpleMatrix;

/**
 * Created by GrIfOn on 30.03.2017.
 */
public class kNNDemo {
    public static void main(String[] args) {

        double[][] data = new double[][] {
                {1, 4, 0},
                {3, 2, 0},
                {4, 5, 0},
                {4, 2, 0},
                {2, 1, 0},

                {6, 10, 1},
                {9, 8, 1},
                {6, 7, 1},
                {7, 9, 1},
                {9, 6, 1},

                {11, 2, 2},
                {15, 4, 2},
                {14, 3.2, 2},
                {12, 5, 2},
                {15, 1, 2},
        };

        SimpleMatrix dataSet = new SimpleMatrix(data);

        XYClassScatterPlot xyClassScatterPlot = new XYClassScatterPlot("data", dataSet);
        xyClassScatterPlot.plot();

        KNN knn = new KNN(3);
        knn.fit(DataSetUtilities.getTrainingSet(dataSet, 0, 1), DataSetUtilities.getAnswersSet(dataSet, 2));

        double[][] predict = new double[][] {
                {10, 3},
                {5, 3},
                {15, 7},
                {1, 3},
                {6, 12},
        };

        knn.predict(new SimpleMatrix(predict));

        xyClassScatterPlot.addExtraData(knn.predict(new SimpleMatrix(predict)));
        xyClassScatterPlot.plot();
    }
}
