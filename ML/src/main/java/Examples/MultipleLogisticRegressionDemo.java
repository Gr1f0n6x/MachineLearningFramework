package Examples;

import Data.DataSetUtilities;
import LearningAlgorithms.LogisticRegression;
import Plot.XYClassScatterPlot;
import org.ejml.simple.SimpleMatrix;

/**
 * Created by GrIfOn on 29.03.2017.
 */
public class MultipleLogisticRegressionDemo {
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

        XYClassScatterPlot xyClassScatterPlot = new XYClassScatterPlot("demo", dataSet);
        xyClassScatterPlot.plot();


        LogisticRegression logisticRegression = new LogisticRegression(1);
        //LogisticRegression logisticRegression = new LogisticRegression(0.1, 5);
        logisticRegression.fit(DataSetUtilities.getTrainingSet(dataSet, 0, 1), DataSetUtilities.getAnswersSet(dataSet, 2), 500, 0);

        logisticRegression.plotCostFunctionHistory();

        double[][] predict = new double[][] {
                {1.2, 3.5},
                {7.6, 8.5},
                {13, 2},
                {10, 7},
                {10, 5},
                {10, 3},
                {7, 7},
                {7, 5},
                {7, 1},
        };

        logisticRegression.predict(new SimpleMatrix(predict));
        xyClassScatterPlot.addExtraData(DataSetUtilities.addColumns(new SimpleMatrix(predict), logisticRegression.predict(new SimpleMatrix(predict))));
        xyClassScatterPlot.plot();

    }
}
