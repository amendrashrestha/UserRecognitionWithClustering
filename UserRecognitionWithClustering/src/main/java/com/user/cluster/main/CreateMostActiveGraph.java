/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.user.cluster.main;

import com.post.graph.CreateUserMostActiveProfileGraph;
import com.user.cluster.IOHandler.IOReadWrite;
import com.user.cluster.clustering.UserDivision;
import com.user.cluster.parser.model.User;
import java.awt.BorderLayout;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author ITE
 */
public class CreateMostActiveGraph {

    public void init() throws FileNotFoundException, IOException {
        IOReadWrite ioReadWrite = new IOReadWrite();
        User user = new User();
        HashMap<Integer, Set> clusterMap = new HashMap<Integer, Set>();

        List<User> userList = ioReadWrite.getAllUsersAsObject();
        userList = user.setCategorizedTimeToUser(userList);
        clusterMap = user.generateUserCluster(userList);
        createFrame(clusterMap);
    }

    public void createFrame(HashMap clusterMap) {
        JFrame frame = new JFrame("Cluster Graph");
        final JPanel gui = new JPanel(new BorderLayout());
        CreateUserMostActiveProfileGraph init = new CreateUserMostActiveProfileGraph();
        JPanel graphPanel = new JPanel();
        graphPanel = init.createTimegraphPanel(clusterMap);
        gui.add(graphPanel);
        frame.setContentPane(gui);
        frame.setSize(710, 400);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String args[]) throws FileNotFoundException, IOException {
        CreateMostActiveGraph main = new CreateMostActiveGraph();
        main.init();
    }
}
