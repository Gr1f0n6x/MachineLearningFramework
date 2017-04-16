package Core.NeuralNetwork.Models;

import Core.NeuralNetwork.Layers.Layer;
import Data.DataSetUtilities;
import org.ejml.simple.SimpleMatrix;

import java.util.ArrayList;

/**
 * Created by GrIfOn on 09.04.2017.
 */
public class Sequential implements Model {
    private ArrayList<Layer> layers = new ArrayList<>();
    private SimpleMatrix lossHistory;

    public Sequential() {
    }

    public void addLayer(Layer layer) {
        layers.add(layer);
    }

    @Override
    public ArrayList<Layer> getLayers() {
        return layers;
    }

    private void conect() {
        for(int layer = 0; layer < layers.size() - 1; ++layer) {
            layers.get(layer).connect(layers.get(layer + 1).getUnits());
        }
    }

    @Override
    public void fit(SimpleMatrix X, SimpleMatrix Y, int epochNum) {
        lossHistory = new SimpleMatrix(epochNum, 2);

        SimpleMatrix X_train = DataSetUtilities.addColumnOfOnes(X);

        this.conect();

        for(int epoch = 0; epoch < epochNum; ++epoch) {
            SimpleMatrix error = null;

            for(int sample = 0; sample < X.numRows(); ++sample) {
                SimpleMatrix output = layers.get(0).feedforward(X_train.extractVector(true, sample));

                // feed forward
                for(int i = 1; i < layers.size(); ++i) {
                    output = layers.get(i).feedforward(output);
                }

                // back propagation
                error = layers.get(layers.size() - 1).computeError(Y.extractVector(true, sample));
                for(int i = layers.size() - 1; i > 1; --i) {
                    error = error.plus(layers.get(i).computeError(error));
                }

                // weights updating
                for(int i = 0; i < layers.size() ; ++i) {
                    layers.get(i).updateWeights(error);
                }
            }
        }
    }

    @Override
    public void predict(SimpleMatrix X) {
        SimpleMatrix X_train = DataSetUtilities.addColumnOfOnes(X);
        SimpleMatrix output = layers.get(0).feedforward(X_train.extractVector(true, 0));

        // feed forward
        for(int i = 1; i < layers.size(); ++i) {
            output = layers.get(i).feedforward(output);
        }

        output.print();
    }
}
