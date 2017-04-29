package Core.NeuralNetwork.Models;

import Core.NeuralNetwork.Layers.Layer;
import Utilities.DataSetUtilities;
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
        for(int layer = 1; layer < layers.size(); ++layer) {
            layers.get(layer).connect(layers.get(layer - 1).getUnits());
        }
    }

    @Override
    public void fit(SimpleMatrix X, SimpleMatrix Y, int epochNum) {
        lossHistory = new SimpleMatrix(epochNum, 2);

        SimpleMatrix X_train = DataSetUtilities.addColumnOfOnes(X);

        this.conect();

        for(int epoch = 0; epoch < epochNum; ++epoch) {
            for(int sample = 0; sample < X.numRows(); ++sample) {
                //------------------------------------------------------------------------//
                //------------------------------------------------------------------------//
                SimpleMatrix output = DataSetUtilities.extractRow(X_train, sample);

                for(int i = 0; i < layers.size(); ++i) {
                    output = layers.get(i).feedforward(output);
                }

                SimpleMatrix delta = DataSetUtilities.extractRow(Y, sample);

                for(int i = layers.size() - 1; i > 0; --i) {
                    delta = layers.get(i).computeError(delta);
                }

                for(int i = layers.size() - 1; i > 0; --i) {
                    layers.get(i).updateWeights();
                }
                //------------------------------------------------------------------------//
                //------------------------------------------------------------------------//
            }
        }
    }

    @Override
    public SimpleMatrix predict(SimpleMatrix X) {
        SimpleMatrix sample = DataSetUtilities.addColumnOfOnes(X);
        SimpleMatrix output = layers.get(0).feedforward(sample);

        for(int i = 1; i < layers.size(); ++i) {
            output = layers.get(i).feedforward(output);
        }

        return output;
    }
}
