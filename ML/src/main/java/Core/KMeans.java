package Core;

import Utilities.DataSetUtilities;
import org.ejml.simple.SimpleMatrix;

/**
 * Created by GrIfOn on 22.05.2017.
 */
public class KMeans {
    private int clusters;
    private SimpleMatrix centroids;
    private SimpleMatrix closest;
    SimpleMatrix[] sums;
    double[] marked;


    public KMeans(int clusters) {
        this.clusters = clusters;
        sums = new SimpleMatrix[clusters];
        marked = new double[clusters];
    }

    public void fit(SimpleMatrix X, int epochNum) {
        init(X);

        for(int epoch = 0; epoch < epochNum; ++epoch) {

            for (int sample = 0; sample < X.numRows(); ++sample) {
                closest.set(sample, 0, findClosestCentroid(X.extractVector(true, sample)));
            }

            for(int cluster = 0; cluster < clusters; ++cluster) {
                sums[cluster] = new SimpleMatrix(1, X.numCols());
            }

            for (int sample = 0; sample < X.numRows(); ++sample) {
                sums[(int) closest.get(sample, 0)] = sums[(int) closest.get(sample, 0)].plus(X.extractVector(true, sample));
                marked[(int) closest.get(sample, 0)]++;
            }

            for(int centroid = 0; centroid < clusters; ++centroid) {
                centroids.setRow(centroid, 0, sums[centroid].divide(marked[centroid]).getMatrix().getData());

                sums[centroid].set(0);
                marked[centroid] = 0;
            }
        }
    }

    public SimpleMatrix getCentroids() {
        return centroids;
    }

    public SimpleMatrix markSamples(SimpleMatrix X) {
        for (int sample = 0; sample < X.numRows(); ++sample) {
            closest.set(sample, 0, findClosestCentroid(X.extractVector(true, sample)));
        }

        return DataSetUtilities.addColumns(X, closest);
    }

    private void init(SimpleMatrix X) {
        centroids = new SimpleMatrix(clusters, X.numCols());
        closest = new SimpleMatrix(X.numRows(), 1);

        SimpleMatrix shuffled = new SimpleMatrix(X);
        DataSetUtilities.shuffle(shuffled);

        for(int centroid = 0; centroid < clusters; ++centroid) {
            centroids.setRow(centroid, 0, shuffled.extractVector(true, centroid).getMatrix().getData());
        }
    }

    private int findClosestCentroid(SimpleMatrix sample) {
        double minDistance = Double.MAX_VALUE;
        int cluster = 0;

        for(int centroid = 0; centroid < clusters; ++centroid) {
            double distance = sample.minus(centroids.extractVector(true, centroid)).elementPower(2).elementSum();

            if(minDistance > distance) {
                minDistance = distance;
                cluster = centroid;
            }
        }

        return cluster;
    }
}
