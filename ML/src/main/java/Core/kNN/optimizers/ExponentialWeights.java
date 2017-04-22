package Core.kNN.optimizers;

import Utilities.Algorithms;
import org.ejml.simple.SimpleMatrix;

/**
 * Created by GrIfOn on 22.04.2017.
 */
public class ExponentialWeights implements Optimizer {
    private double q;

    public ExponentialWeights(double q) {
        if(q <= 0 || q >= 1) {
            throw new IllegalArgumentException("0 < q < 1");
        }

        this.q = q;
    }

    @Override
    public void setNeighbors(SimpleMatrix neighbors) {
        // empty function
    }

    /**
     * q^i
     * @param i
     * @return
     */
    @Override
    public double getWeight(SimpleMatrix currentObject, int i) {
        return Algorithms.binpow(q, i);
    }
}
