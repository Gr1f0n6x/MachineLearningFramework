import Data.DataSet;
import Data.DataSetUtilities;
import LearningAlgorithms.LinearRegression;
import Plot.DataSetPlotter;
import Plot.LineChart;
import org.ejml.simple.SimpleMatrix;

/**
 * Created by GrIfOn on 12.03.2017.
 */
public class Main {
    public static void main(String[] args) {

        double[][] data = new double[][]{
                {1, 1, 1},
                {2, 4, 4},
                {3, 9, 9},
                {4, 16, 16},
                {5, 25, 25},
        };

        SimpleMatrix matrix = new SimpleMatrix(data);
        matrix = DataSetUtilities.addColumnOfOnes(matrix);
        LinearRegression linearRegression = new LinearRegression(0.001);
        linearRegression.fit(DataSetUtilities.getTrainingSet(matrix, 0, 3), DataSetUtilities.getAnswersSet(matrix, 3), 200, 0);
        linearRegression.plotCostFunctionHistory();



//        double[][] ones = new double[][] {
//                {1},
//                {1},
//                {1},
//                {1},
//                {1},
//        };

//        SimpleMatrix thetas = new SimpleMatrix(3, 1);
//        SimpleMatrix dataSet = new SimpleMatrix(data);
//        SimpleMatrix one = new SimpleMatrix(ones);
//
//        one = one.combine(0, one.numCols(), dataSet);
//
//        SimpleMatrix X_train = one.extractMatrix(0, one.numRows(), 0, 3);
//
//        SimpleMatrix Y_train = one.extractMatrix(0, one.numRows(), 3, 4);
//
//        SimpleMatrix J_history = new SimpleMatrix(100, 2);
//
//        for(int i = 0; i < 100; ++i) {
//            //h(theta) current
//            SimpleMatrix h_theta = X_train.mult(thetas);
//
//            //J(theta)
//            double cost = h_theta.minus(Y_train).transpose().mult(h_theta.minus(Y_train)).elementSum()/10;
//
//            J_history.set(i, 0, cost);
//            J_history.set(i, 1, i);
//
//            //gradient descent
//            SimpleMatrix newThetas = new SimpleMatrix(3, 1);
//
//            for(int j = 0; j < X_train.numCols(); ++j) {
//                //System.out.println( h_theta.minus(Y_train).elementMult(X_train.extractVector(false, j)).elementSum() / 5);
//                double Q = thetas.get(j, 0) - 0.001 * h_theta.minus(Y_train).elementMult(X_train.extractVector(false, j)).elementSum() / 5;
//                newThetas.set(j, 0, Q);
//            }
//
//            thetas = newThetas;
//            thetas.print();
//        }
//
//        J_history.print();
//        X_train.mult(thetas).print();
//        DataSet graph = new DataSet(J_history);
//        DataSetPlotter dataSetPlotter = new LineChart("test", graph, 0, 1);
//        dataSetPlotter.plot();
//
//        //predict
//        SimpleMatrix prediction = new SimpleMatrix(new double[][] {
//                {1, 6, 36}
//        });
//        prediction.mult(thetas).print();
//
    }
}
