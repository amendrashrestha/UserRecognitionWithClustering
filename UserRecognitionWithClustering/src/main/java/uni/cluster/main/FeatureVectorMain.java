/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uni.cluster.main;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import uni.post.stylo.featureVector.FeatureVector;

/**
 *
 * @author ITE
 */
public class FeatureVectorMain {
    
    public static void main(String args[]){
        FeatureVector init = new FeatureVector();
        try {
            init.create();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FeatureVectorMain.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | SQLException ex) {
            Logger.getLogger(FeatureVectorMain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
