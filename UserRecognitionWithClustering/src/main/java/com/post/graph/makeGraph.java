/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.post.graph;

import com.user.cluster.IOHandler.IOReadWrite;
import com.user.cluster.clustering.UserDivision;
import com.user.cluster.parser.model.Alias;
import com.user.cluster.parser.model.Posts;
import com.user.cluster.parser.model.User;
import java.awt.Dimension;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import javax.swing.JPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 *
 * @author ITE
 */
public class makeGraph {

    List<String> firstUserTime;
    List<String> secondUserTime;
    String firstUser, secondUser;

    public JPanel createTimegraphPanel(int clickedUser) {
        try {
            FileOutputStream fos = new FileOutputStream("C:/image.PNG");
            dividePost(clickedUser);
            JFreeChart jfreechart = createTimeChart(createTimeDataset(clickedUser));
            ChartUtilities.writeScaledChartAsPNG(fos, jfreechart, 700, 260, 5, 5);
            ChartPanel chartpanel = new ChartPanel(jfreechart);
            chartpanel.setPreferredSize(new Dimension(700, 260));
            return chartpanel;
        } catch (Exception eee) {
            return null;
        }
    }

    private JFreeChart createTimeChart(CategoryDataset xydataset) {
        JFreeChart jfreechart = ChartFactory.createBarChart("User Posts", "Hour", "Posts", xydataset,
                PlotOrientation.VERTICAL, true, true, false);
        return jfreechart;
    }

    private CategoryDataset createTimeDataset(int clickedUser) throws SQLException, FileNotFoundException, IOException {
        DefaultCategoryDataset defaultcategorydataset = new DefaultCategoryDataset();
        HashMap<String, double[]> map = new HashMap<String, double[]>();
        IOReadWrite io = new IOReadWrite();
        User tempClicketUser = io.getUsersAsObject(clickedUser);
        List mainUserPost = tempClicketUser.getUserPost();
        int postSize = mainUserPost.size();

        double[] firstUserVector = Alias.getTimeVectorArray(firstUserTime);
        map.put(firstUser, firstUserVector);
        double[] secondUserVector = Alias.getTimeVectorArray(secondUserTime);
        map.put(secondUser, secondUserVector);

        Iterator<String> kitr = map.keySet().iterator();
        while (kitr.hasNext()) {
            String k = kitr.next();
            double[] post_count = map.get(k);
            int totalPostCount = post_count.length;

            for (Integer i = 0; i < totalPostCount; ++i) {
                double value = (double) post_count[i] * 0.1 / postSize;
                defaultcategorydataset.addValue(value, k, i);
            }
        }
        return defaultcategorydataset;
    }

    private void dividePost(int clickedUser) throws FileNotFoundException, IOException {
        IOReadWrite io = new IOReadWrite();
        UserDivision du = new UserDivision();
        User tempClicketUser = io.getUsersAsObject(clickedUser);
        List<User> userList = du.divideUser(tempClicketUser);

        firstUserTime = new ArrayList<String>();
        secondUserTime = new ArrayList<String>();
        
        User firstUserObj = userList.get(0);
        User secondUserObj = userList.get(1);

        firstUser = String.valueOf(firstUserObj.getId()) + "_A";
        secondUser = String.valueOf(secondUserObj.getId()) + "_B";
        List<Posts> firstUserPost = firstUserObj.getUserPost();
        List<Posts> secondUserPost = secondUserObj.getUserPost();

        for (int i = 0; i < firstUserPost.size(); i++) {
            Posts firstUserPostTime = firstUserPost.get(i);
            String time = firstUserPostTime.getTime();
            firstUserTime.add(time);
        }
        for (int i = 0; i < secondUserPost.size(); i++) {
            Posts secondUserPostTime = secondUserPost.get(i);
            String time = secondUserPostTime.getTime();
            secondUserTime.add(time);
        }
    }
}
