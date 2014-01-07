/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.post.graph;

import com.user.cluster.IOHandler.IOReadWrite;
import com.user.cluster.parser.model.Alias;
import com.user.cluster.parser.model.User;
import java.awt.Dimension;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 *
 * @author ITE
 */
public class CreateUserMostActiveProfileGraph {
    
    public JPanel createTimegraphPanel(HashMap clusterMap) {
        try {
            JFreeChart jfreechart = createTimeChart(createTimeDataset(clusterMap));
            ChartPanel chartpanel = new ChartPanel(jfreechart);
            chartpanel.setPreferredSize(new Dimension(700, 260));
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
            //int totalPostCount = user_count.size();

            for (Integer i = 0; i < user_count.size(); ++i) {
                double value = (double) user_count.size();
                defaultcategorydataset.addValue(value, k, k);
            }
        }
        return defaultcategorydataset;
    }
}
