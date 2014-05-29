/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uni.EISIC14.main;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import uni.EISIC14.analysis.AnalyzeTimeFeature;

/**
 *
 * @author amendrashrestha
 */
public class AnalyzingTimeFeatureMain {

    public static void main(String args[]) throws FileNotFoundException, IOException, SQLException, ParseException {
        AnalyzeTimeFeature init = new AnalyzeTimeFeature();
        init.executeAnalysis();
    }
}
