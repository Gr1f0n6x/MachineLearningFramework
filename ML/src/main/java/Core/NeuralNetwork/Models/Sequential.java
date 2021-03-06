package Core.NeuralNetwork.Models;

import Core.Loss.Loss;
import Core.Loss.MeanSquared;
import Core.NeuralNetwork.Layers.Layer;
import Core.NeuralNetwork.Optimizers.Optimizer;
import Plot.XYLineChart;
import Utilities.DataSetUtilities;
import org.ejml.simple.SimpleMatrix;

import java.util.ArrayList;

/**
 * Created by GrIfOn on 09.04.2017.
 */
public class Sequential implements Model {
    private ArrayList<Layer> layers = new ArrayList<>();
    private SimpleMatrix lossHistory;
    private Optimizer optimizer;

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
    public void compile(Optimizer optimizer) {
        this.optimizer = optimizer;
    }

    @Override
    public void fit(SimpleMatrix X, SimpleMatrix Y, int batchSize, int epochNum) {
        lossHistory = new SimpleMatrix(epochNum, 2);

        SimpleMatrix X_train = DataSetUtilities.addColumnOfOnes(X);

        this.conect();

        Loss loss = new MeanSquared();

        for(int epoch = 0; epoch < epochNum; ++epoch) {
            double error = 0;
            int currentBatch = 0;
            SimpleMatrix delta = new SimpleMatrix(1, Y.numCols());

            for(int sample = 0; sample < X.numRows(); ++sample) {
                //------------------------------------------------------------------------//
                //------------------------------------------------------------------------//
                SimpleMatrix output = DataSetUtilities.extractRow(X_train, sample);

                for(int i = 0; i < layers.size(); ++i) {
                    output = layers.get(i).feedforward(output);
                }

                error += loss.computeCost(output, DataSetUtilities.extractRow(Y, sample));
                delta = delta.plus(DataSetUtilities.extractRow(Y, sample).minus(output));

                currentBatch++;

                if(currentBatch == batchSize) {
                    SimpleMatrix backpropagation = new SimpleMatrix(delta);

                    for(int i = layers.size() - 1; i > 0; --i) {
                        backpropagation = layers.get(i).computeError(backpropagation);
                    }

                    for(int i = layers.size() - 1; i > 0; --i) {
                        layers.get(i).updateWeights(optimizer);
                    }

                    currentBatch = 0;
                    delta.set(0);
                }
                //------------------------------------------------------------------------//
                //------------------------------------------------------------------------//
            }

            lossHistory.set(epoch, 0, epoch);
            lossHistory.set(epoch, 1, error);
        }
    }

    @Override
    public SimpleMatrix predict(SimpleMatrix X) {
        SimpleMatrix samples = DataSetUtilities.addColumnOfOnes(X);
        SimpleMatrix result = new SimpleMatrix(X.numRows(), layers.get(layers.size() - 1).getUnits());

        for(int sample = 0; sample < samples.numRows(); ++sample) {
            SimpleMatrix output = DataSetUtilities.extractRow(samples, sample);

            for(int i = 0; i < layers.size(); ++i) {
                output = layers.get(i).feedforward(output);
            }

            result.setRow(sample, 0, output.getMatrix().getData());
        }

        return result;
    }

    public void printWeights() {
        for(int i = 1; i < layers.size(); ++i) {
            System.out.println(layers.get(i));
            layers.get(i).getWeights().print();
        }
    }

    @Override
    public double test(SimpleMatrix X, SimpleMatrix Y) {
        return 0;
    }

    /**
     *
     */
    public void plotCostFunctionHistory() {
        XYLineChart XYLineChart = new XYLineChart("CostFunction", DataSetUtilities.toArray(lossHistory, 0, 1));
        XYLineChart.plot();
    }
}
