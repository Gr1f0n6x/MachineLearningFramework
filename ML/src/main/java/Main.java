import Core.NeuralNetwork.Activation.Sigmoid;
import Core.NeuralNetwork.Layers.Dense;
import Core.NeuralNetwork.Layers.Input;
import Core.NeuralNetwork.Layers.Output;
import Core.NeuralNetwork.Models.Sequential;
import Utilities.DataSetUtilities;
import org.ejml.simple.SimpleMatrix;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * Created by GrIfOn on 12.03.2017.
 */
public class Main {
    public static void main(String[] args) throws IOException, URISyntaxException {
        double[][] data = new double[][] {
                {0, 0, 0},
                {0, 1, 1},
                {1, 0, 1},
                {1, 1, 0},
        };

        SimpleMatrix dataSet = new SimpleMatrix(data);

        Sequential sequential = new Sequential();
        sequential.addLayer(new Input(2));
        sequential.addLayer(new Dense(new Sigmoid(), 2));
        sequential.addLayer(new Output(new Sigmoid(), 1));

        sequential.fit(DataSetUtilities.getTrainingSet(dataSet, 0, 1), DataSetUtilities.getAnswersSet(dataSet, 2), 1000);

        sequential.predict(new SimpleMatrix(new double[][] {
                {0, 0}
        })).print();
        sequential.predict(new SimpleMatrix(new double[][] {
                {0, 1}
        })).print();

        sequential.predict(new SimpleMatrix(new double[][] {
                {1, 0}
        })).print();
        sequential.predict(new SimpleMatrix(new double[][] {
                {1, 1}
        })).print();
        }
    }