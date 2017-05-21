package Examples;

import Utilities.DataSetUtilities;
import Core.LogisticRegression;
import Plot.XYClassScatterPlot;
import org.ejml.simple.SimpleMatrix;

/**
 * Created by GrIfOn on 19.03.2017.
 */
public class BinaryLogisticRegressionDemo {

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
        };

        SimpleMatrix dataSet = new SimpleMatrix(data);

        XYClassScatterPlot xyClassScatterPlot = new XYClassScatterPlot("demo", dataSet);

        LogisticRegression logisticRegression = new LogisticRegression(0.4, 3);
        logisticRegression.fit(DataSetUtilities.extractMatrix(dataSet, 0, 1), DataSetUtilities.extractMatrix(dataSet, 2), 1000);

        logisticRegression.plotCostFunctionHistory();

        double[][] predict = new double[][] {
                {1.2, 3.5},
                {5.775, 6.2},
                {5.775, 6.4},
                {7.6, 8.5},
        };


        xyClassScatterPlot.addExtraData(DataSetUtilities.addColumns(new SimpleMatrix(predict), logisticRegression.predict(new SimpleMatrix(predict))));

        xyClassScatterPlot.plotHyperline(logisticRegression.getTheta());

        logisticRegression.getTheta().print();
    }
}
