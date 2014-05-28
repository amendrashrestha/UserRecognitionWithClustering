/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uni.EISIC14.main;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import uni.EISIC14.analysis.AnalyzeTime;



/**
 *
 * @author ITE
 */
public class AnalyzingUsersMain {

    public static void main(String args[]) throws FileNotFoundException, IOException, SQLException {
        AnalyzeTime init = new AnalyzeTime();
        init.executeAnalysis();
    }
}
