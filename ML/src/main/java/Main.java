import Data.DataSet;
import Data.DataSetUtilities;
import LearningAlgorithms.LinearRegression;
import Plot.LineChart;
import Plot.ScatterPlot;
import org.ejml.simple.SimpleMatrix;

import java.util.Arrays;

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
        SimpleMatrix X = DataSetUtilities.getTrainingSet(matrix, 0, 2);

        SimpleMatrix Y = DataSetUtilities.getAnswersSet(matrix, 2);


        LinearRegression linearRegression = new LinearRegression(0.001);
        linearRegression.fit(X, Y, 10, 0);
        linearRegression.plotCostFunctionHistory();

        SimpleMatrix test = new SimpleMatrix(new double[][] {
                {1, 1},
                {2, 4},
                {3, 9},
                {4, 16},
                {5, 25}
        });

        SimpleMatrix result = linearRegression.predict(test);
        DataSetUtilities.addColumns(test.extractVector(false, 0), result).print();
        DataSetUtilities.addColumns(X.extractVector(false, 0), Y).print();

        LineChart lineChart = new LineChart("Prediction",
                new SimpleMatrix[] {DataSetUtilities.addColumns(test.extractVector(false, 0), result).transpose(),
                        DataSetUtilities.addColumns(X.extractVector(false, 0), Y).transpose()});

        lineChart.plot();
    }
}
