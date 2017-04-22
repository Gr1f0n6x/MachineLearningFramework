package Examples;

import Core.NeuralNetwork.Layers.Input;
import Core.NeuralNetwork.Layers.Output;
import Core.NeuralNetwork.Models.Sequential;
import Data.DataSetUtilities;
import org.ejml.simple.SimpleMatrix;

/**
 * Created by GrIfOn on 18.04.2017.
 */
public class PerceptronDemo {
    public static void main(String[] args) {
        //        //OR
//        double[][] data = new double[][] {
//                {0, 0, 0},
//                {0, 1, 1},
//                {1, 0, 1},
//                {1, 1, 1},
//        };

//        //AND
//        double[][] data = new double[][] {
//                {0, 0, 0},
//                {0, 1, 0},
//                {1, 0, 0},
//                {1, 1, 1},
//        };

        //NAND
        double[][] data = new double[][] {
                {0, 0, 1},
                {0, 1, 0},
                {1, 0, 0},
                {1, 1, 0},
        };

        SimpleMatrix dataSet = new SimpleMatrix(data);

        Sequential sequential = new Sequential();
        sequential.addLayer(new Input(2));
        sequential.addLayer(new Output(1));

        sequential.fit(DataSetUtilities.getTrainingSet(dataSet, 0, 1), DataSetUtilities.getAnswersSet(dataSet, 2), 5);

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
