/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uni.cluster.graph.main;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import uni.cluster.IOHandler.IOProperties;
import uni.post.graph.controller.CreateActivityGraph;

/**
 *
 * @author ITE
 */
public class ActivityGraphMain extends JFrame{
    
    String FACPath = IOProperties.All_ACTIVITY_BASE_PATH + "\\" + IOProperties.FIRST_ACTIVITY_FOLDER_NAME;
    String SCPath = IOProperties.All_ACTIVITY_BASE_PATH + "\\" + IOProperties.SLEEPING_FOLDER_NAME;
    String SACPath = "";
    
    public ActivityGraphMain(){
        CreateActivityGraph init = new CreateActivityGraph();
        try {
//            init.FirstActivityGraph(FACPath);
            init.SleepingClusterGraph(SCPath);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ActivityGraphMain.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ActivityGraphMain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
       
    public static void main(String args[]){
        ActivityGraphMain initMain = new ActivityGraphMain();
        
    }
    
}
