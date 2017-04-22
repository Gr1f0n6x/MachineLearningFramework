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
            SimpleMatrix gradient = null;

            for(int sample = 0; sample < X.numRows(); ++sample) {
                SimpleMatrix currentSample = DataSetUtilities.extractRow(X_train, sample);
                SimpleMatrix output = layers.get(0).feedforward(currentSample);

                for(int i = 1; i < layers.size(); ++i) {
                    output = layers.get(i).feedforward(output);
                }

                SimpleMatrix idealOutput = DataSetUtilities.extractRow(Y, sample);

                error = layers.get(layers.size() - 1).computeError(idealOutput);
                //gradient = gradient == null ? currentSample.scale(error.elementSum()) : gradient.plus(currentSample.scale(error.elementSum()));
                gradient = gradient == null ? layers.get(layers.size() - 1).computeGradient(currentSample) : gradient.plus(layers.get(layers.size() - 1).computeGradient(currentSample));

                for(int i = layers.size() - 2; i > 0; --i) {
                    error = layers.get(i).computeError(error);
                    //gradient = gradient.plus(currentSample.scale(error.elementSum()));
                    gradient = gradient.plus(layers.get(layers.size() - 1).computeGradient(currentSample));
                }
            }

            for(int i = 0; i < layers.size() - 1; ++i) {
                layers.get(i).updateWeights(gradient);
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
