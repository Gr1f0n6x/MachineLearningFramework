package Core.NeuralNetwork.Models;

import Core.NeuralNetwork.Layers.Layer;
import org.ejml.simple.SimpleMatrix;

import java.util.ArrayList;

/**
 * Created by GrIfOn on 09.04.2017.
 */
public class Sequential implements Model {
    private ArrayList<Layer> layers = new ArrayList<>();

    public Sequential() {
    }

    public void addLayer(Layer layer) {
        layers.add(layer);
    }

    @Override
    public ArrayList<Layer> getLayers() {
        return layers;
    }

    public void conect() {
        for(int layer = 0; layer < layers.size() - 1; ++layer) {
            layers.get(layer).connect(layers.get(layer + 1).getNeurons().numRows());
        }
    }

    @Override
    public void fit(SimpleMatrix X, SimpleMatrix Y, int epochNum) {
    }

    @Override
    public void predict(SimpleMatrix X) {

    }
}
