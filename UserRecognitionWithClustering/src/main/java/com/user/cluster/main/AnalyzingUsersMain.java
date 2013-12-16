/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.user.cluster.main;

import com.user.cluster.analysis.UserAnalysis;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;


/**
 *
 * @author ITE
 */
public class AnalyzingUsersMain {

    public static void main(String args[]) throws FileNotFoundException, IOException, SQLException {
        UserAnalysis init = new UserAnalysis();
        init.executeAnalysis();
    }
}
