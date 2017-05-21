package Examples;

import Core.NeuralNetwork.Activation.Identity;
import Core.NeuralNetwork.Layers.Dense;
import Core.NeuralNetwork.Layers.Input;
import Core.NeuralNetwork.Layers.Output;
import Core.NeuralNetwork.Models.Sequential;
import Core.NeuralNetwork.Optimizers.SGD;
import Utilities.DataSetUtilities;
import org.ejml.simple.SimpleMatrix;

/**
 * Created by GrIfOn on 21.05.2017.
 */
public class MLPRegressionDemo {
    public static void main(String[] args) {
        double[][] data = new double[100][5];

        for (int i = 0; i < 100; ++i) {
            data[i][0] = i;
            data[i][1] = i;
            data[i][2] = i / 2.;
            data[i][3] = - i / 2.;
            data[i][4] = - i;
        }

        SimpleMatrix dataSet = new SimpleMatrix(data);
        dataSet.print();

        Sequential sequential = new Sequential();
        sequential.addLayer(new Input(1));
        sequential.addLayer(new Dense(2));
        sequential.addLayer(new Dense(3));
        sequential.addLayer(new Output(4));

        sequential.compile(new SGD(0.000000003, 0, 0, false));
        sequential.fit(DataSetUtilities.extractMatrix(dataSet, 0), DataSetUtilities.extractMatrix(dataSet, 1, 4), 1, 10000);
        sequential.plotCostFunctionHistory();
        sequential.predict(DataSetUtilities.extractMatrix(dataSet, 0)).print();

        sequential.printWeights();
    }
}
