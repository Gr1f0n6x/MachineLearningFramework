import Plot.XYClassScatterPlot;
import Plot.XYScatterPlot;
import org.apache.commons.collections.MultiHashMap;
import org.apache.commons.collections.map.MultiKeyMap;
import org.apache.commons.collections.map.MultiValueMap;
import org.ejml.simple.SimpleMatrix;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.DoublePredicate;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;

/**
 * Created by GrIfOn on 12.03.2017.
 */
public class Main {
    public static void main(String[] args) {

//        double[][] data = new double[][]{
//                {1, 1, 1},
//                {2, 4, 4},
//                {3, 9, 9},
//                {4, 16, 16},
//                {5, 25, 25},
//        };
//
//        SimpleMatrix matrix = new SimpleMatrix(data);
//        SimpleMatrix X = DataSetUtilities.getTrainingSet(matrix, 0, 2);
//
//        SimpleMatrix Y = DataSetUtilities.getAnswersSet(matrix, 2);
//
//
//        LinearRegression linearRegression = new LinearRegression(0.001, 100);
//        linearRegression.fit(X, Y, 10, 0);
//        linearRegression.plotCostFunctionHistory();
//
//        SimpleMatrix test = new SimpleMatrix(new double[][] {
//                {1, 1},
//                {2, 4},
//                {3, 9},
//                {4, 16},
//                {5, 25}
//        });
//
//        SimpleMatrix result = linearRegression.predict(test);
//        DataSetUtilities.addColumns(test.extractVector(false, 0), result).print();
//        DataSetUtilities.addColumns(X.extractVector(false, 0), Y).print();
//
//        XYLineChart lineChart = new XYLineChart("Prediction",
//                new SimpleMatrix[] {DataSetUtilities.addColumns(test.extractVector(false, 0), result).transpose(),
//                        DataSetUtilities.addColumns(X.extractVector(false, 0), Y).transpose()});
//
//        lineChart.plot();

        double[][] data = new double[][] {
                {1, 4, 0},
                {3, 2, 0},
                {4, 5, 0},
                {4, 2, 0},
                {2, 1, 0},
                {6, 10, 1},
                {9, 8, 1},
                {6, 7, 1},
                {7, 9, 1},
                {9, 6, 1},
                {11, 11, 2},
                {15, 12, 2},
                {14, 13, 2},
                {12, 11, 2},
                {15, 15, 2},
        };

        SimpleMatrix dataSet = new SimpleMatrix(data);
        dataSet.print();


//        Map<Integer, SimpleMatrix> classMap = new MultiValueMap();
//        for(int i = 0; i < dataSet.numRows(); ++i) {
//            classMap.put((int) dataSet.get(i, dataSet.numCols() - 1), dataSet.extractVector(true, i));
//        }
//
//        System.out.println(classMap.size());
//        for(Map.Entry<Integer, SimpleMatrix> entry : classMap.entrySet()) {
//
//            List<SimpleMatrix> values = (List<SimpleMatrix>) entry.getValue();
//            System.out.println("Key: " + entry.getKey());
//
//            values.stream().forEach((x) -> System.out.println(x.get(0, 0) + " " + x.get(0, 1)));
//        }



        XYClassScatterPlot xyClassScatterPlot = new XYClassScatterPlot("demo", dataSet);
        xyClassScatterPlot.plot();
    }
}
