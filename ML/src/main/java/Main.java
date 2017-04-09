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
//
//        double[][] data = new double[][] {
//                {1, 4, 0},
//                {3, 2, 0},
//                {4, 5, 0},
//                {4, 2, 0},
//                {2, 1, 0},
//
//                {6, 10, 1},
//                {9, 8, 1},
//                {6, 7, 1},
//                {7, 9, 1},
//                {9, 6, 1},
//
//                {11, 2, 2},
//                {15, 4, 2},
//                {14, 3.2, 2},
//                {12, 5, 2},
//                {15, 1, 2},
//        };
//
//        SimpleMatrix dataSet = new SimpleMatrix(data);
//
//        System.out.println(dataSet.get(21));

        Sequential sequential = new Sequential();
        sequential.addLayer(new Input(2));
        sequential.addLayer(new Dense(3));
        sequential.addLayer(new Dense(4));
        sequential.addLayer(new Output(1));
        sequential.conect();

        for(Layer layer : sequential.getLayers()) {
            System.out.println(layer);
            for(SimpleMatrix theta : layer.getThetas()) {
                theta.print();
            }

        }
    }
}
