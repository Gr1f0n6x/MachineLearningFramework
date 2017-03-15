package Plot;

import Data.DataSet;
import org.ejml.simple.SimpleMatrix;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;

import javax.swing.*;

/**
 * Created by GrIfOn on 15.03.2017.
 */
public class ScatterPlot extends DataSetPlotter {

    public ScatterPlot(String title, DataSet dataSet) {
        super(title);

        setXyDataset(dataSet);
        this.setContentPane(getChartPanel());
    }

    public ScatterPlot(String title, double[][] dataSet) {
        super(title);

        setXyDataset(dataSet);
        this.setContentPane(getChartPanel());
    }

    public ScatterPlot(String title, DataSet dataSet, int xColumnm, int yColumn) {
        super(title);

        setXyDataset(dataSet, xColumnm, yColumn);
        this.setContentPane(getChartPanel());
    }

    public ScatterPlot(String title, SimpleMatrix dataSet) {
        super(title);

        setXyDataset(dataSet);
        this.setContentPane(getChartPanel());
    }

    public ScatterPlot(String title, SimpleMatrix dataSet, int xColumn, int yColumn) {
        super(title);

        setXyDataset(dataSet, xColumn, yColumn);
        this.setContentPane(getChartPanel());
    }

    protected JPanel getChartPanel() {
        JFreeChart chart = ChartFactory.createScatterPlot("Data", "X", "Y", xyDataset, PlotOrientation.VERTICAL, true, true, false);
        return new ChartPanel(chart);
    }
}
