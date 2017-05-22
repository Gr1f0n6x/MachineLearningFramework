package Examples;

import Core.NeuralNetwork.Activation.Sigmoid;
import Core.NeuralNetwork.Layers.Dense;
import Core.NeuralNetwork.Layers.Input;
import Core.NeuralNetwork.Layers.Output;
import Core.NeuralNetwork.Models.Sequential;
import Core.NeuralNetwork.Optimizers.SGD;
import Data.DataSet;
import Utilities.DataSetUtilities;
import org.ejml.simple.SimpleMatrix;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * Created by GrIfOn on 22.05.2017.
 */
public class MLPClassificationDemo {
    public static void main(String[] args) throws URISyntaxException, IOException {
        DataSet data = new DataSet(MLPClassificationDemo.class.getResource("/iris.csv").toURI());

        data.replaceByValue("Iris-setosa", "0");
        data.replaceByValue("Iris-versicolor", "1");
        data.replaceByValue("Iris-virginica", "2");

        SimpleMatrix dataSet = data.getMatrix();
        SimpleMatrix[] cvTrain = DataSetUtilities.getCrossValidationAndTrainSets(dataSet, 0.2, true);
        SimpleMatrix train = cvTrain[0];
        SimpleMatrix test = cvTrain[1];

        SimpleMatrix Y = DataSetUtilities.toCategorical(DataSetUtilities.extractMatrix(train, 4), 3);

        Sequential sequential = new Sequential();
        sequential.addLayer(new Input(4));
        sequential.addLayer(new Dense(new Sigmoid(), 5));
        sequential.addLayer(new Dense(new Sigmoid(), 5));
        sequential.addLayer(new Dense(new Sigmoid(), 5));
        sequential.addLayer(new Output(new Sigmoid(), 3));

        sequential.compile(new SGD(2, 0.1, 0.01, false));
        sequential.fit(DataSetUtilities.extractMatrix(train, 0, 3), Y, 1, 1000);
        sequential.plotCostFunctionHistory();

        Y.print();
        sequential.predict(DataSetUtilities.extractMatrix(dataSet, 0, 3)).print();
    }
}
