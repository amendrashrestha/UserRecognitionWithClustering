/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uni.post.graph.controller;

import java.awt.Dimension;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.StackedBarRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.TextAnchor;

/**
 *
 * @author ITE
 */
public class CreateUserMostActiveProfileGraph {

    public JPanel createTimegraphPanel(HashMap clusterMap) {
        try {
            JFreeChart jfreechart = createTimeChart(createTimeDataset(clusterMap));
            StackedBarRenderer renderer = new StackedBarRenderer(false);
            renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
            renderer.setBaseItemLabelsVisible(true);
            renderer.setBasePositiveItemLabelPosition(new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12, TextAnchor.BASELINE_LEFT));
            jfreechart.getCategoryPlot().setRenderer(renderer);
            
            ChartPanel chartpanel = new ChartPanel(jfreechart);
            chartpanel.setPreferredSize(new Dimension(600, 400));
            return chartpanel;
        } catch (Exception eee) {
            return null;
        }
    }

    private JFreeChart createTimeChart(CategoryDataset xydataset) {
        JFreeChart jfreechart = ChartFactory.createBarChart("First Activity Cluster in Graph", "Cluster", "User Count", xydataset,
                PlotOrientation.VERTICAL, true, true, false);
        return jfreechart;
    }

    private CategoryDataset createTimeDataset(HashMap<Integer, Set> clusterMap) throws SQLException, FileNotFoundException, IOException {
        DefaultCategoryDataset defaultcategorydataset = new DefaultCategoryDataset();

        Iterator<Integer> kitr = clusterMap.keySet().iterator();
        while (kitr.hasNext()) {
            Integer k = kitr.next();
            Set user_count = clusterMap.get(k);

            for (Integer i = 0; i < user_count.size(); ++i) {
                double value = (double) user_count.size();
                defaultcategorydataset.addValue(value, k, k);
            } 
        }
        return defaultcategorydataset;
    }
}
