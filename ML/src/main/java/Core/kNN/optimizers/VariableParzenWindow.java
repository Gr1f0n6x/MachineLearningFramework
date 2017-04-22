package Core.kNN.optimizers;

import org.ejml.simple.SimpleMatrix;

/**
 * Created by GrIfOn on 22.04.2017.
 */
public class VariableParzenWindow implements Optimizer  {
    private SimpleMatrix X;
    private SimpleMatrix neighbors;

    public VariableParzenWindow(SimpleMatrix x) {
        X = x;
    }

    @Override
    public void setNeighbors(SimpleMatrix neighbors) {
        this.neighbors = neighbors;
    }

    @Override
    public double getWeight(SimpleMatrix currentObject, int i) {
        double distanceCurr = Math.sqrt(X.extractVector(true, i).minus(currentObject).elementPower(2).elementSum());
        double distanceNext = Math.sqrt(X.extractVector(true, i + 1).minus(currentObject).elementPower(2).elementSum());

        return distanceCurr / distanceNext;
    }
}
