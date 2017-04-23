package Core.Loss;

import org.ejml.simple.SimpleMatrix;

/**
 * Created by GrIfOn on 10.04.2017.
 */
public interface Loss {
    double computeCost(SimpleMatrix predicted, SimpleMatrix expected, SimpleMatrix thetas);
    double computeCost(SimpleMatrix predicted, SimpleMatrix expected, SimpleMatrix thetas, double lambda);
}
