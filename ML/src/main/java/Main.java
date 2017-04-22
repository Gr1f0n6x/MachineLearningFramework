import Core.NeuralNetwork.Activation.Identity;
import Core.NeuralNetwork.Layers.Input;
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
                {1, 1},
                {2, 4},
                {3, 9},
                {4, 16},
                {5, 25},
        };

        SimpleMatrix dataSet = new SimpleMatrix(data);

        Sequential sequential = new Sequential();
        sequential.addLayer(new Input(1));
        sequential.addLayer(new Output(new Identity(), 1));

        sequential.fit(DataSetUtilities.getTrainingSet(dataSet, 0), DataSetUtilities.getAnswersSet(dataSet, 1), 1);

        sequential.predict(new SimpleMatrix(new double[][] {
                {6}
        })).print();
        sequential.predict(new SimpleMatrix(new double[][] {
                {7}
        })).print();
        }
    }
