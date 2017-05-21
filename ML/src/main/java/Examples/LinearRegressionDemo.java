package Examples;

import Data.DataSet;
import Utilities.DataSetUtilities;
import Core.LinearRegression;
import Plot.XYLineChart;
import org.ejml.simple.SimpleMatrix;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * Created by GrIfOn on 19.03.2017.
 */
public class LinearRegressionDemo {

    public static void main(String[] args) throws URISyntaxException, IOException {
        DataSet dataSet = new DataSet(kNNDemo.class.getResource("/cement.csv").toURI());

        SimpleMatrix[] cvTrain = DataSetUtilities.getCrossValidationAndTrainSets(dataSet.getMatrix(), 0.3, false);
        SimpleMatrix train = cvTrain[0];
        SimpleMatrix test = cvTrain[1];

        LinearRegression linearRegression = new LinearRegression(0.000001, 10);
        linearRegression.fit(DataSetUtilities.extractMatrix(train, 1, 8), DataSetUtilities.extractMatrix(train, 9), 5000);
        linearRegression.plotCostFunctionHistory();

        SimpleMatrix result = linearRegression.predict(DataSetUtilities.extractMatrix(test, 1, 8));
        XYLineChart lineChart = new XYLineChart("Prediction",
                        new SimpleMatrix[] {DataSetUtilities.addColumns(test.extractVector(false, 0), result),
                        DataSetUtilities.addColumns(dataSet.getMatrix().extractVector(false, 0), dataSet.getMatrix().extractVector(false, 9))}, true);

        lineChart.plot();
    }
}
