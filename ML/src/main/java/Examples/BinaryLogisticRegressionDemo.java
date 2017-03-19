package Examples;

import Data.DataSetUtilities;
import LearningAlgorithms.LogisticRegression;
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
        xyClassScatterPlot.plot();

        LogisticRegression logisticRegression = new LogisticRegression(0.4);
        logisticRegression.fit(DataSetUtilities.getTrainingSet(dataSet, 0, 2), DataSetUtilities.getAnswersSet(dataSet, 2), 100, 0);

        logisticRegression.plotCostFunctionHistory();

        double[][] predict = new double[][] {
                {1.2, 3.5}
        };

        logisticRegression.predict(new SimpleMatrix(predict)).print();

        predict = new double[][] {
                {7.6, 8.5}
        };

       logisticRegression.predict(new SimpleMatrix(predict)).print();
    }
}
