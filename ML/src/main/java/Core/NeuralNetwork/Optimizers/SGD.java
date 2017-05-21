package Core.NeuralNetwork.Optimizers;

import org.ejml.simple.SimpleMatrix;

/**
 * Created by GrIfOn on 22.05.2017.
 */
public class SGD implements Optimizer {
    private double learningRate;
    private double momentum;
    private double decay;
    private boolean nesterov;

    public SGD(double learningRate, double momentum, double decay, boolean nesterov) {
        if(learningRate < 0 || momentum < 0 || decay < 0)
            throw new IllegalArgumentException("Arguments should be >= 0");

        this.learningRate = learningRate;
        this.momentum = momentum;
        this.decay = decay;
        this.nesterov = nesterov;
    }

    public double getLearningRate() {
        return learningRate;
    }

    public double getMomentum() {
        return momentum;
    }

    public double getDecay() {
        return decay;
    }

    public boolean isNesterov() {
        return nesterov;
    }

    @Override
    public SimpleMatrix updateWeights(SimpleMatrix delta, SimpleMatrix thetas, SimpleMatrix previousDelta) {
        thetas = thetas.plus(delta.scale(learningRate));

        if(previousDelta != null) {
            thetas = thetas.plus(previousDelta.scale(momentum));
        }

        if(learningRate - decay > 0) {
            learningRate -= decay;
        }

        return thetas;
    }
}
