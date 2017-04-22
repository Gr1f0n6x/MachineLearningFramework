package Plot;

import Data.DataSet;
import org.ejml.simple.SimpleMatrix;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.plot.PlotOrientation;

import javax.swing.*;

/**
 * Created by GrIfOn on 15.03.2017.
 */
public class XYLineChart extends XYDataSetFrame {

    public XYLineChart(String title, DataSet dataSet) {
        super(title);

        setXyDataset(dataSet);
        this.setContentPane(getChartPanel(title));
    }

    public XYLineChart(String title, double[][] dataSet) {
        super(title);

        setXyDataset(dataSet);
        this.setContentPane(getChartPanel(title));
    }

    public XYLineChart(String title, DataSet dataSet, int xColumnm, int yColumn) {
        super(title);

        setXyDataset(dataSet, xColumnm, yColumn);
        this.setContentPane(getChartPanel(title));
    }

    public XYLineChart(String title, SimpleMatrix dataSet, boolean transpose) {
        super(title);

        setXyDataset(dataSet, transpose);
        this.setContentPane(getChartPanel(title));
    }

    public XYLineChart(String title, SimpleMatrix dataSet, int xColumn, int yColumn) {
        super(title);

        setXyDataset(dataSet, xColumn, yColumn);
        this.setContentPane(getChartPanel(title));
    }

    public XYLineChart(String title, SimpleMatrix X, SimpleMatrix Y) {
        super(title);

        setXyDataset(X, Y);
        this.setContentPane(getChartPanel(title));
    }

    public XYLineChart(String title, SimpleMatrix[] dataSet, boolean transpose) {
        super(title);

        setXyDataset(dataSet, transpose);
        this.setContentPane(getChartPanel(title));
    }

    public XYLineChart(String title, DataSet[] dataSet) {
        super(title);

        setXyDataset(dataSet);
        this.setContentPane(getChartPanel(title));
    }

    public XYLineChart(String title, double[][][] dataSet) {
        super(title);

        setXyDataset(dataSet);
        this.setContentPane(getChartPanel(title));
    }

    @Override
    protected JPanel getChartPanel(String title) {
        chart = ChartFactory.createXYLineChart(title, "X", "Y", xyDataset, PlotOrientation.VERTICAL, true, true, false);
        return new ChartPanel(chart);
    }
}
