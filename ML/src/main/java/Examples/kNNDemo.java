package Examples;

import Data.DataSetUtilities;
import Core.KNN;
import Plot.XYClassScatterPlot;
import org.ejml.simple.SimpleMatrix;

/**
 * Created by GrIfOn on 30.03.2017.
 */
public class kNNDemo {
    public static void main(String[] args) {

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

        SimpleMatrix dataSet = new SimpleMatrix(data);

        XYClassScatterPlot xyClassScatterPlot = new XYClassScatterPlot("data", dataSet);
        xyClassScatterPlot.plot();

        KNN knn = new KNN(3);
        knn.LOO(DataSetUtilities.getTrainingSet(dataSet, 0, 1), DataSetUtilities.getAnswersSet(dataSet, 2), 10, 1);

        knn.fit(DataSetUtilities.getTrainingSet(dataSet, 0, 1), DataSetUtilities.getAnswersSet(dataSet, 2));

        double[][] predict = new double[][] {
                {10, 3},
                {5, 3},
                {15, 7},
                {1, 3},
                {6, 12},
                {9, 5},
                {8, 4},
        };

        knn.predict(new SimpleMatrix(predict));

        xyClassScatterPlot.addExtraData(knn.predict(new SimpleMatrix(predict)));
        xyClassScatterPlot.plot();
    }
}
