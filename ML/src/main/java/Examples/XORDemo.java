package Examples;

import Core.NeuralNetwork.Activation.Tanh;
import Core.NeuralNetwork.Layers.Dense;
import Core.NeuralNetwork.Layers.Input;
import Core.NeuralNetwork.Layers.Output;
import Core.NeuralNetwork.Models.Sequential;
import Core.NeuralNetwork.Optimizers.SGD;
import Utilities.DataSetUtilities;
import org.ejml.simple.SimpleMatrix;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * Created by GrIfOn on 23.05.2017.
 */
public class XORDemo {
    public static void main(String[] args) throws IOException, URISyntaxException {
        double[][] data = new double[][] {
                {0, 0, -1},
                {0, 1, 1},
                {1, 0, 1},
                {1, 1, -1},
        };

        SimpleMatrix dataSet = new SimpleMatrix(data);

        Sequential sequential = new Sequential();
        sequential.addLayer(new Input(2));
        sequential.addLayer(new Dense(new Tanh(),10));
        sequential.addLayer(new Output(new Tanh(), 1));

        sequential.compile(new SGD(0.1, 0, 0, false));
        sequential.fit(DataSetUtilities.extractMatrix(dataSet, 0, 1), DataSetUtilities.extractMatrix(dataSet, 2), 1, 1000);
        sequential.plotCostFunctionHistory();
        sequential.predict(DataSetUtilities.extractMatrix(dataSet, 0, 1)).print();
    }
}
