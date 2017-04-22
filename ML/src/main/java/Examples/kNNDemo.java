package Examples;

import Data.DataSet;
import Plot.ClassScatterMultipleDimensions;
import Utilities.DataSetUtilities;
import Core.kNN.KNN;
import Plot.XYClassScatterPlot;
import org.ejml.simple.SimpleMatrix;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * Created by GrIfOn on 30.03.2017.
 */
public class kNNDemo {
    public static void main(String[] args) throws URISyntaxException, IOException {
        DataSet dataSet = new DataSet(kNNDemo.class.getResource("/iris.csv").toURI());

        dataSet.replaceByValue("Iris-setosa", "0");
        dataSet.replaceByValue("Iris-versicolor", "1");
        dataSet.replaceByValue("Iris-virginica", "2");

        SimpleMatrix[] cvTrain = DataSetUtilities.getCrossValidationAndTrainSets(dataSet.getMatrix(), 0.3, true);
        SimpleMatrix train = cvTrain[0];
        SimpleMatrix test = cvTrain[1];

        KNN knn = new KNN(3);
        int optimalK = knn.LOO(DataSetUtilities.getTrainingSet(train, 0, 3), DataSetUtilities.getAnswersSet(train, 4), 100, 0);
        knn.setNeighbors(optimalK);

        knn.fit(DataSetUtilities.getTrainingSet(train, 0, 3), DataSetUtilities.getAnswersSet(train, 4));
        double accuracy = knn.test(DataSetUtilities.getTrainingSet(test, 0, 3), DataSetUtilities.getAnswersSet(test, 4));
        System.out.println(accuracy);

        ClassScatterMultipleDimensions plotter = new ClassScatterMultipleDimensions("Iris", train, new String[] {
                "sepal length",
                "sepal width",
                "petal length",
                "petal width",
        });

        plotter.plot();

    }
}
