package Core.kNN.optimizers;

import org.ejml.simple.SimpleMatrix;

/**
 * Created by GrIfOn on 22.04.2017.
 */
public class StaticParzenWindow implements Optimizer  {
    private double windowSize;
    private SimpleMatrix neighbors;

    public StaticParzenWindow(double windowSize) {
        if(windowSize <= 0) {
            throw new IllegalArgumentException("windowSize should be > 0");
        }

        this.windowSize = windowSize;
    }

    @Override
    public void setNeighbors(SimpleMatrix neighbors) {
        this.neighbors = neighbors;
    }

    @Override
    public double getWeight(SimpleMatrix currentObject, int i) {
        double distance = Math.sqrt(neighbors.extractVector(true, i).minus(currentObject).elementPower(2).elementSum());

        return distance / windowSize;
    }
}