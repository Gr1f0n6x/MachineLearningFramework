package Core.NeuralNetwork.Activation;

import org.ejml.simple.SimpleMatrix;

/**
 * Created by GrIfOn on 10.04.2017.
 */
public class PRelu implements Activation {
    private double parameter;

    public PRelu(double parametr) {
        this.parameter = parametr;
    }

    public double getParameter() {
        return parameter;
    }

    public void setParameter(double parameter) {
        this.parameter = parameter;
    }

    @Override
    public SimpleMatrix activation(SimpleMatrix Z) {
        SimpleMatrix result = new SimpleMatrix(Z);

        for(int row = 0; row < result.numRows(); ++row) {
            result.set(row, 0, result.get(row, 0) >= 0 ? result.get(row, 0) : result.get(row, 0) * parameter);
        }

        return result;
    }

    @Override
    public SimpleMatrix derivative(SimpleMatrix Z) {
        SimpleMatrix derivative = new SimpleMatrix(Z);

        for(int row = 0; row < derivative.numRows(); ++row) {
            derivative.set(row, 0, derivative.get(row, 0) >= 0 ? 1 : parameter);
        }

        return derivative;
    }
}
