package uni.cluster.main;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import uni.cluster.IOHandler.IOReadWrite;
import uni.cluster.clustering.FirstActivityCluster;
import uni.cluster.clustering.SleepingCluster;
import uni.cluster.parser.model.User;

/**
 * This class generates the Sleeping Cluster taking users of each individual
 * from First Activity Cluster.
 *
 * @author ITE
 */
public class SleepingClusterMain {

    Set uniqueSleepingUsersList = new HashSet();

    public static void main(String[] args) throws FileNotFoundException, IOException {
        SleepingClusterMain init = new SleepingClusterMain();
        init.createSleepingCluster();
    }

    public void createSleepingCluster() throws FileNotFoundException, IOException {
        IOReadWrite ioReadWrite = new IOReadWrite();
        FirstActivityCluster facObj = new FirstActivityCluster();
        SleepingCluster scObj = new SleepingCluster();
        List<User> allUserUnDividedUserList = ioReadWrite.getAllUsersAsObject();
        List<FirstActivityCluster> facList = facObj.readFirstActivityCluster();
        int FACSize = 6;
        List<SleepingCluster> allscList;

        for (int i = 0; i < FACSize; i++) {
            allscList = new ArrayList<>();
//            allscList = scObj.getIndiviudalSleepingClusterUserWithSplit(facList, i, allUserUnDividedUserList);
            allscList = scObj.getIndiviudalSleepingClusterUserWOSplit(facList, i, allUserUnDividedUserList);
            processSCUsers(allscList, (i + 1));
        }
        System.out.println("The total number of distinct sleeping users is: " + uniqueSleepingUsersList.size());
    }

    public final void processSCUsers(List<SleepingCluster> allscList, int FACcluster) throws IOException {
        SleepingCluster scObj = new SleepingCluster();
        System.out.println("The number of the user in " + FACcluster + "th Sleeping Cluster is:  " + allscList.size());

        for (int i = 0; i < allscList.size(); i++) {
            SleepingCluster scList = allscList.get(i);

            System.out.println("The id of the user is:  " + scList.getUserID());
            int[] timeVec = scList.getSleepingCluster();
            for (int j = 0; j < timeVec.length; j++) {
                if (timeVec[j] == 1) {
                    scObj.writeSingleSleepingCluster(scList, FACcluster, (j+1));
                }
            }
            System.out.println("The time vector is:  " + String.valueOf(timeVec[0]) + " " + String.valueOf(timeVec[1]) + " " + String.valueOf(timeVec[2]) + " "
                    + String.valueOf(timeVec[3]) + " " + String.valueOf(timeVec[4]) + " " + String.valueOf(timeVec[5]));
            uniqueSleepingUsersList.add(scList.getUserID());
        }
        System.out.println("************************************************");
    }
}
