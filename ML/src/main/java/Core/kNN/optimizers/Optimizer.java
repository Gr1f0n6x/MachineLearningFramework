package Core.kNN.optimizers;

import org.ejml.simple.SimpleMatrix;

/**
 * Created by GrIfOn on 22.04.2017.
 */
public interface Optimizer {
    double getWeight(SimpleMatrix object, int i);
    void setNeighbors(SimpleMatrix neighbors);
}
