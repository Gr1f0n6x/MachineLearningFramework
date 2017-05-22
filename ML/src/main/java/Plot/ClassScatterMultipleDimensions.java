package Plot;

import Data.DataSet;
import Plot.Interfaces.Plotter;
import org.apache.commons.collections.map.MultiValueMap;
import org.ejml.simple.SimpleMatrix;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.UnknownKeyException;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

import javax.swing.*;
import java.awt.*;
import java.util.*;

/**
 * Created by GrIfOn on 23.04.2017.
 */
public class ClassScatterMultipleDimensions extends ApplicationFrame implements Plotter {

    public ClassScatterMultipleDimensions(String title, DataSet dataSet, String[] columnsName) {
        super(title);

        this.setContentPane(preparePanels(dataSet.getMatrix(), title, columnsName));
    }

    public ClassScatterMultipleDimensions(String title, SimpleMatrix dataSet, String[] columnsName) {
        super(title);

        this.setContentPane(preparePanels(dataSet, title, columnsName));
    }

    public ClassScatterMultipleDimensions(String title, SimpleMatrix dataSet, SimpleMatrix centroids, String[] columnsName) {
        super(title);

        this.setContentPane(preparePanels(dataSet, centroids, title, columnsName));
    }

    private JPanel preparePanels(SimpleMatrix X, String title, String[] columnsName) {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(X.numCols() / 2, X.numCols() / 2, 5, 5));

        for(int i = 0; i < X.numCols(); ++i) {
            for(int j = i + 1; j < X.numCols() - 1; ++j) {
                XYSeriesCollection xySeriesCollection = getDataSetForPanel(X, i, j);
                JFreeChart chart = ChartFactory.createScatterPlot(title, columnsName[i], columnsName[j], xySeriesCollection, PlotOrientation.VERTICAL, true, true, false);
                panel.add(new ChartPanel(chart));
            }
        }

        return panel;
    }

    private JPanel preparePanels(SimpleMatrix X, SimpleMatrix centroids, String title, String[] columnsName) {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(X.numCols() / 2, X.numCols() / 2, 5, 5));

        for(int i = 0; i < X.numCols(); ++i) {
            for(int j = i + 1; j < X.numCols() - 1; ++j) {
                XYSeriesCollection xySeriesCollection = getDataSetForPanel(X, i, j);
                xySeriesCollection.addSeries(getCentroidsSeries(centroids, i, j));
                JFreeChart chart = ChartFactory.createScatterPlot(title, columnsName[i], columnsName[j], xySeriesCollection, PlotOrientation.VERTICAL, true, true, false);
                panel.add(new ChartPanel(chart));
            }
        }

        return panel;
    }

    private XYSeriesCollection getDataSetForPanel(SimpleMatrix matrix, int x1, int x2) {
        XYSeriesCollection xyDataset = new XYSeriesCollection();

        Map<String, SimpleMatrix> classMap = new MultiValueMap();
        for(int i = 0; i < matrix.numRows(); ++i) {
            classMap.put(String.valueOf(matrix.get(i, matrix.numCols() - 1)),
                    matrix.extractVector(true, i));
        }

        for(Map.Entry<String, SimpleMatrix> entry : classMap.entrySet()) {
            java.util.List<SimpleMatrix> values = (java.util.List<SimpleMatrix>) entry.getValue();
            XYSeries xySeries;

            try {
                xySeries = xyDataset.getSeries(entry.getKey());
                final XYSeries finalXySeries = xySeries;
                values.stream().forEach((x) -> finalXySeries.add(x.get(0, x1), x.get(0, x2)));

            } catch (UnknownKeyException e) {
                xySeries = new XYSeries(entry.getKey());
                final XYSeries finalXySeries = xySeries;
                values.stream().forEach((x) -> finalXySeries.add(x.get(0, x1), x.get(0, x2)));

                xyDataset.addSeries(xySeries);
            }
        }

        return xyDataset;
    }

    private XYSeries getCentroidsSeries(SimpleMatrix centroids, int x1, int x2) {
        XYSeries series = new XYSeries(x1 + " " + x2);

        for(int row = 0; row < centroids.numRows(); ++row) {
            series.add(centroids.get(row, x1), centroids.get(row, x2));
        }

        return series;
    }

    @Override
    public void plot() {
        this.pack();
        RefineryUtilities.centerFrameOnScreen(this);
        this.setVisible(true);
    }
}
