/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uni.cluster.graph.main;

import uni.cluster.main.*;
import java.awt.BorderLayout;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JPanel;
import uni.post.graph.controller.CreateClusterGraph;

/**
 *
 * @author ITE
 */
public class UserClusterGraphMain {

    public void createFrame() {
        JFrame frame = new JFrame("Cluster Graph");
        final JPanel gui = new JPanel(new BorderLayout());
        CreateClusterGraph init = new CreateClusterGraph();
        JPanel graphPanel = new JPanel();
        graphPanel = init.createTimegraphPanel();
        gui.add(graphPanel);
        frame.setContentPane(gui);
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String args[]) throws FileNotFoundException, IOException {
        UserClusterGraphMain main = new UserClusterGraphMain();
        main.createFrame();
    }
}
