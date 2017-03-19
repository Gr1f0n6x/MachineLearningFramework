import Data.DataSetUtilities;
import LearningAlgorithms.LogisticRegression;
import Plot.XYClassScatterPlot;
import Plot.XYLineChart;
import Plot.XYScatterPlot;
import org.apache.commons.collections.MultiHashMap;
import org.apache.commons.collections.map.MultiKeyMap;
import org.apache.commons.collections.map.MultiValueMap;
import org.ejml.simple.SimpleMatrix;

import java.util.ArrayList;
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
                {11, 2, 2},
                {15, 4, 2},
                {14, 3.2, 2},
                {12, 5, 2},
                {15, 1, 2},
                {11, 12, 3},
                {15, 10, 3},
                {14, 13, 3},
                {12, 15, 3},
                {15, 14, 3},
        };

        SimpleMatrix dataSet = new SimpleMatrix(data);

        XYClassScatterPlot xyClassScatterPlot = new XYClassScatterPlot("demo", dataSet);
        xyClassScatterPlot.plot();


        LogisticRegression logisticRegression = new LogisticRegression(0.04);
        logisticRegression.fit(DataSetUtilities.getTrainingSet(dataSet, 0, 2), DataSetUtilities.getAnswersSet(dataSet, 2), 100, 0);

        logisticRegression.plotCostFunctionHistory();

        double[][] predict = new double[][] {
                {1.2, 3.5}
        };


        xyClassScatterPlot.addExtraData(DataSetUtilities.addColumns(new SimpleMatrix(predict), logisticRegression.predict(new SimpleMatrix(predict))));

        predict = new double[][] {
                {7.6, 8.5}
        };

        xyClassScatterPlot.addExtraData(DataSetUtilities.addColumns(new SimpleMatrix(predict), logisticRegression.predict(new SimpleMatrix(predict))));
        xyClassScatterPlot.plotHyperline(DataSetUtilities.getTrainingSet(dataSet, 0), logisticRegression.getThetas());
    }
}
