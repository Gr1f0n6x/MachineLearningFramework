package Examples;

import Utilities.DataSetUtilities;
import Core.kNN.KNN;
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

        SimpleMatrix dataSet = new SimpleMatrix(data);

        XYClassScatterPlot xyClassScatterPlot = new XYClassScatterPlot("data", dataSet);

        KNN knn = new KNN(3);
        int optimalK = knn.LOO(DataSetUtilities.getTrainingSet(dataSet, 0, 1), DataSetUtilities.getAnswersSet(dataSet, 2), 34, 0);
        knn.setNeighbors(optimalK);

        knn.fit(DataSetUtilities.getTrainingSet(dataSet, 0, 1), DataSetUtilities.getAnswersSet(dataSet, 2));

        double[][] predict = new double[][] {
                {8.5, 2},
        };

        xyClassScatterPlot.addExtraData(knn.predict(new SimpleMatrix(predict)));
        xyClassScatterPlot.plot();
    }
}
