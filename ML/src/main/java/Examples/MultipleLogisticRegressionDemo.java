package Examples;

import Data.DataSet;
import Plot.ClassScatterMultipleDimensions;
import Utilities.DataSetUtilities;
import Core.LogisticRegression;
import Plot.XYClassScatterPlot;
import org.ejml.simple.SimpleMatrix;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * Created by GrIfOn on 29.03.2017.
 */
public class MultipleLogisticRegressionDemo {
    public static void main(String[] args) throws URISyntaxException, IOException {
        DataSet data = new DataSet(MultipleLogisticRegressionDemo.class.getResource("/iris.csv").toURI());

        data.replaceByValue("Iris-setosa", "0");
        data.replaceByValue("Iris-versicolor", "1");
        data.replaceByValue("Iris-virginica", "2");

        SimpleMatrix dataSet = data.getMatrix();
        SimpleMatrix[] cvTrain = DataSetUtilities.getCrossValidationAndTrainSets(dataSet, 0.2, true);
        SimpleMatrix train = cvTrain[0];
        SimpleMatrix test = cvTrain[1];

        // sometimes it return less than 20% accuracy, sometimes more than 90%
        LogisticRegression logisticRegression = new LogisticRegression(0.1);
        logisticRegression.fit(DataSetUtilities.extractMatrix(train, 0, 3), DataSetUtilities.extractMatrix(train, 4), 1000);
        logisticRegression.plotCostFunctionHistory();

        double accuracy = logisticRegression.test(DataSetUtilities.extractMatrix(test, 0, 3), DataSetUtilities.extractMatrix(test, 4));
        System.out.println(accuracy);

//        ClassScatterMultipleDimensions plotter = new ClassScatterMultipleDimensions("Iris", train, new String[] {
//                "sepal length",
//                "sepal width",
//                "petal length",
//                "petal width",
//        });
//
//        plotter.plot();

    }
}
