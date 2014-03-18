/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uni.cluster.main;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import uni.cluster.IOHandler.IOReadWrite;
import uni.cluster.clustering.FirstActivityCluster;
import uni.cluster.clustering.UserDivision;
import uni.cluster.parser.model.User;

/**
 *
 * @author ITE
 */
public class FirstActivityMain {

    public static void main(String[] args) throws FileNotFoundException, IOException {

        IOReadWrite ioReadWrite = new IOReadWrite();
        User user = new User();
        FirstActivityCluster fac = new FirstActivityCluster();
        List<User> unDividedUserList = ioReadWrite.getAllUsersAsObject();

        //For passing limited number of sorted users  
        List<User> tempUsers = ioReadWrite.returnLimitedSortedUser(unDividedUserList, 3);

        /**
         * First Activity Cluster by splitting users
         */
        UserDivision userDivision = new UserDivision();
        List dividedUserList = userDivision.divideAllUser(tempUsers);
        user.setCategorizedTimeToUser(dividedUserList);
        user.generateUserFirstActivityCluster(dividedUserList);
        List facUsers = fac.getUserInSameClusterForFirstActivityClusterAsFACObject(dividedUserList);
        fac.writeFirstActivityCluster(facUsers);
        //List dividedUserList = userDivision.divideAllUser(unDividedUserList);
        
        /*********************************************************************************
        /**
         * First Activity Cluster without splitting users
         */
       /* List users = user.setCategorizedTimeToUser(tempUsers);
        List facUsers = fac.generateUserFirstActivityClusterMaxPost(users);
        // This function writes the firstActivityCluster into file
        fac.writeFirstActivityCluster(facUsers);

        *************************************************************************************
        /*
         for (int i = 0; i < facList.size(); i++) {
         fac = (FirstActivityCluster) facList.get(i);
         System.out.println("The id of the user is:  " + fac.getUserID());
         int[] timeVec = fac.getPostTimeVector();
         System.out.println("The time vector is:  " + String.valueOf(timeVec[0]) + " " + String.valueOf(timeVec[1]) + " " + String.valueOf(timeVec[2]) + " "
         + String.valueOf(timeVec[3]) + " " + String.valueOf(timeVec[4]) + " " + String.valueOf(timeVec[5]));
         }

         System.out.println("The number of users before first Activity Cluster is: " + String.valueOf(unDividedUserList.size()));
         System.out.println("The number of users in first Activity Cluster is: " + String.valueOf(facList.size()));
         * */
    }
}
