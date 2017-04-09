package Examples;

import Data.DataSetUtilities;
import Core.LogisticRegression;
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
        logisticRegression.fit(DataSetUtilities.getTrainingSet(dataSet, 0, 1), DataSetUtilities.getAnswersSet(dataSet, 2), 1000);

        logisticRegression.plotCostFunctionHistory();

        double[][] predict = new double[][] {
                {6, 4},
                {10, 4},
                {11, 4},
                {12, 4},
        };

        xyClassScatterPlot.addExtraData(DataSetUtilities.addColumns(new SimpleMatrix(predict), logisticRegression.predict(new SimpleMatrix(predict))));
        xyClassScatterPlot.plot();

    }
}
