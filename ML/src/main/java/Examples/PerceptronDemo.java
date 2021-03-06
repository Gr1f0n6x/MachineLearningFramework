package Examples;

import Core.NeuralNetwork.Activation.Sigmoid;
import Core.NeuralNetwork.Layers.Input;
import Core.NeuralNetwork.Layers.Output;
import Core.NeuralNetwork.Models.Sequential;
import Core.NeuralNetwork.Optimizers.SGD;
import Utilities.DataSetUtilities;
import org.ejml.simple.SimpleMatrix;

/**
 * Created by GrIfOn on 18.04.2017.
 */
public class PerceptronDemo {
    public static void main(String[] args) {
        double[][] data = new double[][] {
                {0, 0, 0},
                {0, 1, 0},
                {1, 0, 0},
                {1, 1, 1},
        };

        SimpleMatrix dataSet = new SimpleMatrix(data);

        Sequential sequential = new Sequential();
        sequential.addLayer(new Input(2));
        sequential.addLayer(new Output(new Sigmoid(), 1));

        sequential.compile(new SGD(1, 0, 0, false));
        sequential.fit(DataSetUtilities.extractMatrix(dataSet, 0, 1), DataSetUtilities.extractMatrix(dataSet, 2), 1, 1000);
        sequential.plotCostFunctionHistory();
        sequential.predict(DataSetUtilities.extractMatrix(dataSet, 0, 1)).print();
    }
}
