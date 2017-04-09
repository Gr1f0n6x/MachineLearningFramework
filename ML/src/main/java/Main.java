import Core.NeuralNetwork.Layers.Dense;
import Core.NeuralNetwork.Layers.Input;
import Core.NeuralNetwork.Layers.Layer;
import Core.NeuralNetwork.Layers.Output;
import Core.NeuralNetwork.Models.Sequential;
import Data.DataSetUtilities;
import org.ejml.simple.SimpleMatrix;

/**
 * Created by GrIfOn on 12.03.2017.
 */
public class Main {
    public static void main(String[] args) {

        double[][] data = new double[][] {
                {1, 2},
                {2, 4},
                {3, 6},
                {4, 8},
                {5, 10},
        };

        SimpleMatrix dataSet = new SimpleMatrix(data);

        Sequential sequential = new Sequential();
        sequential.addLayer(new Input(1));
        sequential.addLayer(new Dense(3));
        sequential.addLayer(new Dense(4));
        sequential.addLayer(new Output(1));

        sequential.fit(DataSetUtilities.getTrainingSet(dataSet, 0), DataSetUtilities.getAnswersSet(dataSet, 1), 1);

        }
    }
