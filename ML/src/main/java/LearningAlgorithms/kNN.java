package LearningAlgorithms;

import Data.DataNormalization;
import org.ejml.simple.SimpleMatrix;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by GrIfOn on 12.03.2017.
 */
public class KNN {
    private SimpleMatrix X_train;
    private SimpleMatrix Y_train;
    private SimpleMatrix normalized_X;
    private List<DistanceAndClass> distances;
    private int neighbors;

    public KNN(int neighbors) {
        this.neighbors = neighbors;
        distances = new ArrayList<>();
    }

    public int getNeighbors() {
        return neighbors;
    }

    public void setNeighbors(int neighbors) {
        this.neighbors = neighbors;
    }

    public List<DistanceAndClass> getDistances() {
        return distances;
    }

    private long calculateDistances(SimpleMatrix prediction) {
        for(int row = 0; row < normalized_X.numRows(); ++row) {
            distances.add(new DistanceAndClass(Math.sqrt(normalized_X.extractVector(true, row).minus(prediction).elementPower(2).elementSum()), (int)Y_train.get(row, 0)));
        }

        Collections.sort(distances);

        Map<Integer, Long> counter = distances.stream().limit(neighbors).
                collect(Collectors.groupingBy(DistanceAndClass::getClassNumber, Collectors.counting()));

        distances.clear();

        return counter.entrySet().stream().max((x, y) -> x.getValue().compareTo(y.getValue())).get().getKey();
    }

    public void fit(SimpleMatrix x_train, SimpleMatrix y_train) {
        Y_train = y_train;
        X_train = x_train;
        normalized_X = DataNormalization.minMaxNormalization(X_train);
    }

    public SimpleMatrix predict(SimpleMatrix X) {
        SimpleMatrix normalized = DataNormalization.minMaxNormalization(X_train, X);
        SimpleMatrix answer = new SimpleMatrix(normalized.numRows(), normalized.numCols() + 1);

        for(int row = 0; row < normalized.numRows(); ++row) {
            answer.setRow(row, 0, X.extractVector(true, row).getMatrix().getData());
            answer.set(row, answer.numCols() - 1, calculateDistances(normalized.extractVector(true, row)));
        }

        return answer;
    }

    public class DistanceAndClass implements Comparable<DistanceAndClass> {
        private double distance;
        private int classNumber;

        private DistanceAndClass(double distance, int classNumber) {
            this.distance = distance;
            this.classNumber = classNumber;
        }

        public double getDistance() {
            return distance;
        }

        public void setDistance(double distance) {
            this.distance = distance;
        }

        public int getClassNumber() {
            return classNumber;
        }

        public void setClassNumber(int classNumber) {
            this.classNumber = classNumber;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            DistanceAndClass that = (DistanceAndClass) o;

            if (Double.compare(that.distance, distance) != 0) return false;
            return classNumber == that.classNumber;

        }

        @Override
        public int hashCode() {
            int result;
            long temp;
            temp = Double.doubleToLongBits(distance);
            result = (int) (temp ^ (temp >>> 32));
            result = 31 * result + classNumber;
            return result;
        }

        @Override
        public String toString() {
            return "DistanceAndClass{" +
                    "distance=" + distance +
                    ", classNumber=" + classNumber +
                    '}';
        }

        @Override
        public int compareTo(DistanceAndClass o) {
            if(this.distance < o.distance) {
                return -1;
            } else if(this.distance > o.distance) {
                return 1;
            } else {
                return 0;
            }
        }
    }
}
