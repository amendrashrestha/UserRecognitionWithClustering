/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uni.cluster.main;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import uni.cluster.IOHandler.IOProperties;
import uni.cluster.IOHandler.IOReadWrite;
import uni.cluster.clustering.FirstActivityCluster;
import uni.cluster.clustering.SecondActivityCluster;
import uni.cluster.clustering.SleepingCluster;
import uni.cluster.parser.model.User;

/**
 *
 * @author ITE
 */
public class SecondActivityClusterMain {

    public static void main(String[] args) throws IOException {
        SecondActivityClusterMain init = new SecondActivityClusterMain();
        init.createSecondActivityCluster();
    }

    public void createSecondActivityCluster() throws FileNotFoundException, IOException {
        IOReadWrite ioReadWrite = new IOReadWrite();
        FirstActivityCluster facObj = new FirstActivityCluster();
        SecondActivityCluster sacObj = new SecondActivityCluster();

        List<User> allUserUnDividedUserList = ioReadWrite.getAllUsersAsObject();
        List<FirstActivityCluster> facList = facObj.readFirstActivityCluster();
        String folderPath = IOProperties.All_ACTIVITY_BASE_PATH + "\\" + IOProperties.SLEEPING_FOLDER_NAME;
        // This gives us the sleeping cluster data
        List file = ioReadWrite.getAllFilesInADirectory(folderPath);

        for (int i = 0; i < file.size(); i++) {
            System.out.println("FileName: " + file.get(i));
            String filename = folderPath + '\\' + file.get(i);
            List<SleepingCluster> scList = ioReadWrite.readSleepingClusterData(filename + IOProperties.SLEEPING_FILE_EXTENSION);
            
            List<SecondActivityCluster> scClusterSACList;
            String FacSc = file.get(i).toString().replaceAll("[\\D]", "");

//                scClusterSACList = sacObj.getIndiviudalSACUserWithSplit(facList, scList, i, allUserUnDividedUserList);
            scClusterSACList = sacObj.getIndiviudalSACUserWOSPlit(facList, scList, i, allUserUnDividedUserList);

            processSAC(scClusterSACList, FacSc);
        }
        String sacFolderPath = IOProperties.All_ACTIVITY_BASE_PATH + IOProperties.SECOND_ACTIVITY_FOLDER_NAME;
        ioReadWrite.readSecondActivityClusterData(sacFolderPath);
    }

    public void processSAC(List<SecondActivityCluster> scClusterSACList, String FacScName) throws IOException {
        SecondActivityCluster sacObj = new SecondActivityCluster();
        for (int k = 0; k < scClusterSACList.size(); k++) {
            SecondActivityCluster saUser = (SecondActivityCluster) scClusterSACList.get(k);
            int[] clusterVec = scClusterSACList.get(k).getUserCluster();
            for (int j = 0; j < clusterVec.length; j++) {
                if (clusterVec[j] == 1) {
                    sacObj.writeSecondActivityCluster(saUser, FacScName, j + 1);
                }
            }
        }
    }
}
/*for (int j=0; j<allscList.size(); j++){
 scClusterSACList = new ArrayList<List<SecondActivityCluster>>();
 scList = allscList.get(j);
 for(int i=0; i<6; i++){
 scClusterSACList.add(sacObj.getIndiviudalClusterUser(facList, scList, j, i, allUserUnDividedUserList));
 //                scClusterSACList.add(sacObj.getIndiviudalClusterUser(facList, scList, j, i));
 }
 sacObj.writeSecondActivityCluster(scClusterSACList, j+1);
 }
        
 String sacFolderPath = IOProperties.All_ACTIVITY_BASE_PATH + IOProperties.SECOND_ACTIVITY_FOLDER_NAME;
 //int sacUserSize = ioReadWrite.returnSecondActivityClusterUserSize(sacFolderPath);
 //System.out.println("Final User Size: " + sacUserSize);
        
 ioReadWrite.readSecondActivityClusterData(sacFolderPath);    */

/*for (int i = 0; i < noFileSC; i++) {
 //          allscList.add(scObj.getIndiviudalClusterUser(facList, i));
 allscList.add(scObj.getIndiviudalClusterUserWOSplit(facList, i, allUserUnDividedUserList));
 }
       
 List<List<SecondActivityCluster>> scClusterSACList = new ArrayList<List<SecondActivityCluster>>();
 List<SleepingCluster> scList = new ArrayList<SleepingCluster>();
 for (int j=0; j<allscList.size(); j++){
 scClusterSACList = new ArrayList<List<SecondActivityCluster>>();
 scList = allscList.get(j);
 for(int i=0; i<6; i++){
 scClusterSACList.add(sacObj.getIndiviudalClusterUser(facList, scList, j, i, allUserUnDividedUserList));
 //                scClusterSACList.add(sacObj.getIndiviudalClusterUser(facList, scList, j, i));
 }
 sacObj.writeSecondActivityCluster(scClusterSACList, j+1);
 }
        
 String sacFolderPath = IOProperties.All_ACTIVITY_BASE_PATH + IOProperties.SECOND_ACTIVITY_FOLDER_NAME;
 //int sacUserSize = ioReadWrite.returnSecondActivityClusterUserSize(sacFolderPath);
 //System.out.println("Final User Size: " + sacUserSize);
        
 ioReadWrite.readSecondActivityClusterData(sacFolderPath);      
 }*/
