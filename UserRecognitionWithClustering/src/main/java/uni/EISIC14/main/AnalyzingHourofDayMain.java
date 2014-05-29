/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uni.EISIC14.main;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import uni.EISIC14.analysis.AnalyzeHourofDay;



/**
 *
 * @author ITE
 */
public class AnalyzingHourofDayMain {

    public static void main(String args[]) throws FileNotFoundException, IOException, SQLException {
        AnalyzeHourofDay init = new AnalyzeHourofDay();
        init.executeAnalysis();
    }
}
