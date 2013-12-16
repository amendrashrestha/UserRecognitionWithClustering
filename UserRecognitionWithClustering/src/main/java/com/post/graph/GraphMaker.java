/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.post.graph;

import com.user.cluster.IOHandler.IOReadWrite;
import com.user.cluster.parser.model.User;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 *
 * @author ITE
 */
public final class GraphMaker {

    public JPanel tempGraphPanel;
    JPanel graphPanel = new JPanel();
    JButton btnOK;
    makeGraph maker = new makeGraph();

    public GraphMaker() throws SQLException, FileNotFoundException, IOException {
        Border redline = BorderFactory.createLineBorder(Color.red);
        final Border greenline = BorderFactory.createLineBorder(Color.green);
        JButton btnNew = new JButton("New");
        JLabel selectID = new JLabel("Select User");
        selectID.setSize(20, 100);
        IOReadWrite io = new IOReadWrite();
        //getting userID from text file and setting into combo box
        final JComboBox userID = new JComboBox();
        List<User> userList = io.getAllUsersAsObject();
        for (int i = 0; i < userList.size(); i++) {
            User user = userList.get(i);
            userID.addItem(user.getId());
        }

        userID.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                int tempUser = (Integer) userID.getSelectedItem();
                tempGraphPanel = maker.createTimegraphPanel(tempUser);
                graphPanel.add(tempGraphPanel);
                graphPanel.setBorder(greenline);
            }
        });

        final JTextField txtUserID = new JTextField();
        txtUserID.setPreferredSize(new Dimension(150, 20));

        btnOK = new JButton("OK");
        btnOK.setEnabled(false);

        txtUserID.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent de) {
                warn();
            }

            @Override
            public void removeUpdate(DocumentEvent de) {
                warn();
            }

            @Override
            public void changedUpdate(DocumentEvent de) {
                warn();
            }

            public void warn() {
                String text = txtUserID.getText();
                if (text.length() > 0) {
                    btnOK.setEnabled(true);
                } else {
                    btnOK.setEnabled(false);
                }
            }
        });

        btnOK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                String tempUser = txtUserID.getText();
                int User = Integer.parseInt(tempUser);
                tempGraphPanel = maker.createTimegraphPanel(User);
                graphPanel.add(tempGraphPanel);
                graphPanel.setBorder(greenline);
            }
        });

        JFrame frame = new JFrame("Graph Maker");

        final JPanel gui = new JPanel(new BorderLayout());
        gui.setBorder(redline);

        JPanel plafComponents = new JPanel(
                new FlowLayout(FlowLayout.LEFT, 5, 20));

        plafComponents.add(selectID);
        plafComponents.add(txtUserID);
        plafComponents.add(btnOK);
        plafComponents.add(userID);
        plafComponents.add(btnNew);

        btnNew.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                System.out.println("New Clicked");
                graphPanel.remove(tempGraphPanel);
                graphPanel.validate();
                graphPanel.repaint();
            }
        });

        gui.add(plafComponents, BorderLayout.NORTH);
        gui.add(graphPanel, BorderLayout.SOUTH);

        frame.setContentPane(gui);

        frame.setSize(710, 400);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    public static void main(String args[]) throws SQLException, FileNotFoundException, IOException {
        GraphMaker init = new GraphMaker();
    }
}
