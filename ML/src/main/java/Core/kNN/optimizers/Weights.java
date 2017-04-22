package Core.kNN.optimizers;

import org.ejml.simple.SimpleMatrix;

/**
 * Created by GrIfOn on 22.04.2017.
 */
public class Weights implements Optimizer {
    private int k;

    public Weights(int k) {
        if(k <= 0) {
            throw new IllegalArgumentException("k should be > 0");
        }

        this.k = k;
    }

    @Override
    public void setNeighbors(SimpleMatrix neighbors) {
        // empty function
    }

    /**
     * (k + 1 - i) / k
     * @param i
     * @return
     */
    @Override
    public double getWeight(SimpleMatrix currentObject, int i) {
        return (k + 1 - i) / k;
    }
}
