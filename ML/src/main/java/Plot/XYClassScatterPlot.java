package Plot;

import Data.DataSet;
import org.ejml.simple.SimpleMatrix;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.DefaultXYDataset;

import javax.swing.*;
import java.util.stream.DoubleStream;

/**
 * Created by GrIfOn on 19.03.2017.
 */
public class XYClassScatterPlot extends XYClassDataSetFrame {

    public XYClassScatterPlot(String title, DataSet dataSet) {
        super(title);

        setXyDataset(dataSet);
        this.setContentPane(getChartPanel(title));
    }

    public XYClassScatterPlot(String title, DataSet dataSet, int x1Columnm, int x2Column, int yColumn) {
        super(title);

        setXyDataset(dataSet, x1Columnm, x2Column, yColumn);
        this.setContentPane(getChartPanel(title));
    }

    public XYClassScatterPlot(String title, SimpleMatrix dataSet) {
        super(title);

        setXyDataset(dataSet);
        this.setContentPane(getChartPanel(title));
    }

    public XYClassScatterPlot(String title, SimpleMatrix dataSet, int x1Columnm, int x2Column, int yColumn) {
        super(title);

        setXyDataset(dataSet, x1Columnm, x2Column, yColumn);
        this.setContentPane(getChartPanel(title));
    }

    @Override
    protected JPanel getChartPanel(String title) {
        chart = ChartFactory.createScatterPlot(title, "X", "Y", xyDataset, PlotOrientation.VERTICAL, true, true, false);
        return new ChartPanel(chart);
    }

    public void plotHyperline(SimpleMatrix thetas) {
        XYPlot plotter = new XYPlot();
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer(false, true);
        plotter.setDataset(xyDataset);
        plotter.setRenderer(renderer);

        plotter.setDomainAxis(new NumberAxis("X"));
        plotter.setRangeAxis(new NumberAxis("Y"));

        plotter.setOrientation(PlotOrientation.VERTICAL);
        plotter.setRangeGridlinesVisible(true);
        plotter.setDomainGridlinesVisible(true);


        XYLineAndShapeRenderer line = new XYLineAndShapeRenderer();

        DefaultXYDataset hyperline = new DefaultXYDataset();

        hyperline.addSeries(0, new double[][] {
                {-thetas.get(0, 0) / thetas.get(1, 0), 0},
                {0, -thetas.get(0, 0) / thetas.get(2, 0)},
        });

        plotter.setDataset(1, hyperline);
        plotter.setRenderer(1, line);

        chart = new JFreeChart(plotter);
        this.setContentPane(new ChartPanel(chart));

        plot();
    }

    public void plotHyperline(SimpleMatrix[] thetas) {
        XYPlot plotter = new XYPlot();
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer(false, true);
        plotter.setDataset(xyDataset);
        plotter.setRenderer(renderer);

        plotter.setDomainAxis(new NumberAxis("X"));
        plotter.setRangeAxis(new NumberAxis("Y"));
        plotter.mapDatasetToRangeAxis(0, 0);

        plotter.setOrientation(PlotOrientation.VERTICAL);
        plotter.setRangeGridlinesVisible(true);
        plotter.setDomainGridlinesVisible(true);


        for (int i = 0; i < thetas.length; ++i) {

            XYLineAndShapeRenderer line = new XYLineAndShapeRenderer();
            DefaultXYDataset hyperline = new DefaultXYDataset();

            double[][] points = new double[][] {
                    {-thetas[i].get(0, 0) / thetas[i].get(1, 0), 0},
                    {0, -thetas[i].get(0, 0) / thetas[i].get(2, 0)},
            };

            hyperline.addSeries(0, points);


            plotter.setDataset(i + 1, hyperline);
            plotter.setRenderer(i + 1, line);


            plotter.setRangeAxis(i + 1, new NumberAxis("HyperlaneX" + i));
            plotter.setDomainAxis(i + 1, new NumberAxis("HyperlaneY" + i));

            //Map the data to the appropriate axis
            plotter.mapDatasetToRangeAxis(i + 1, i + 1);
            plotter.mapDatasetToDomainAxis(i + 1, i + 1);
        }

            chart = new JFreeChart(plotter);
            this.setContentPane(new ChartPanel(chart));

            plot();
    }
}
