import Core.NeuralNetwork.Activation.Sigmoid;
import Core.NeuralNetwork.Layers.Dense;
import Core.NeuralNetwork.Layers.Input;
import Core.NeuralNetwork.Layers.Output;
import Core.NeuralNetwork.Models.Sequential;
import Data.DataSet;
import Examples.MultipleLogisticRegressionDemo;
import Utilities.DataSetUtilities;
import org.ejml.simple.SimpleMatrix;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * Created by GrIfOn on 12.03.2017.
 */
public class Main {
    public static void main(String[] args) throws IOException, URISyntaxException {
        DataSet data = new DataSet(MultipleLogisticRegressionDemo.class.getResource("/iris.csv").toURI());

        data.replaceByValue("Iris-setosa", "0");
        data.replaceByValue("Iris-versicolor", "1");
        data.replaceByValue("Iris-virginica", "2");

        SimpleMatrix dataSet = data.getMatrix();
        SimpleMatrix[] cvTrain = DataSetUtilities.getCrossValidationAndTrainSets(dataSet, 0.2, true);
        SimpleMatrix train = cvTrain[0];
        SimpleMatrix test = cvTrain[1];

        DataSetUtilities.toCategorical(DataSetUtilities.extractMatrix(train, 4), 3).print();
//
//        Sequential sequential = new Sequential();
//        sequential.addLayer(new Input(4));
//        sequential.addLayer(new Dense(new Sigmoid(), 5));
//        sequential.addLayer(new Dense(new Sigmoid(), 5));
//        sequential.addLayer(new Dense(new Sigmoid(), 5));
//        sequential.addLayer(new Output(new Sigmoid(), 3));
//
//        sequential.fit(DataSetUtilities.extractMatrix(train, 0, 3), DataSetUtilities.extractMatrix(train, 4), 0.1, 1000);
//        sequential.plotCostFunctionHistory();
//        sequential.predict(DataSetUtilities.extractMatrix(dataSet, 0, 1)).print();


    }
}
