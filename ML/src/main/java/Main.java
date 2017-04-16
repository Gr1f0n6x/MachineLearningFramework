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

        //OR
        double[][] data = new double[][] {
                {0, 0, 0},
                {0, 1, 1},
                {1, 0, 1},
                {1, 1, 1},
        };

        SimpleMatrix dataSet = new SimpleMatrix(data);

        Sequential sequential = new Sequential();
        sequential.addLayer(new Input(2));
        sequential.addLayer(new Output(1));

        sequential.fit(DataSetUtilities.getTrainingSet(dataSet, 0, 1), DataSetUtilities.getAnswersSet(dataSet, 2), 1);

        sequential.predict(new SimpleMatrix(new double[][] {
                {0, 0}
        }));
        sequential.predict(new SimpleMatrix(new double[][] {
                {0, 1}
        }));
        }
    }
