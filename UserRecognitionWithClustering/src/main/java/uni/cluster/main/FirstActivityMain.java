/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uni.cluster.main;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.List;
import uni.cluster.IOHandler.IOReadWrite;
import uni.cluster.clustering.FirstActivityCluster;
import uni.cluster.clustering.UserDivision;
import uni.cluster.parser.model.User;

/**
 * This class creates First Activity Cluster of users either by splitting them
 * or without splitting.
 * @author ITE
 */
public class FirstActivityMain {

    public static void main(String[] args) throws FileNotFoundException, IOException, ParseException {

        IOReadWrite ioReadWrite = new IOReadWrite();
        User user = new User();
        FirstActivityCluster fac = new FirstActivityCluster();
        List<User> unDividedUserList = ioReadWrite.getAllUsersAsObject();

        //For passing limited number of sorted users  
        List<User> tempUsers = ioReadWrite.returnLimitedSortedUser(unDividedUserList, 1);

        /**
         * First Activity Cluster by splitting users
         */
       /*UserDivision userDivision = new UserDivision();
        List dividedUserList = userDivision.divideAllUser(tempUsers);
        user.setCategorizedTimeToUser(dividedUserList);
        user.setCategorizedDayToUser(dividedUserList);
        user.generateUserFirstActivityCluster(dividedUserList);
        List facUsers = fac.getUserInSameClusterForFirstActivityClusterAsFACObject(dividedUserList);
        fac.writeFirstActivityCluster(facUsers);
        //List dividedUserList = userDivision.divideAllUser(unDividedUserList);
        
        /*********************************************************************************
        /**
         * First Activity Cluster without spliting users
         */
        List<User> users = user.setCategorizedTimeToUser(tempUsers);
        //users = user.setCategorizedDayToUser(tempUsers);
        //users = user.setCategorizedMonthToUser(tempUsers);
        users = user.setCategorizedDayOfMonthToUser(tempUsers);
        System.out.println("User posts Week " + Arrays.toString(users.get(0).getClassifiedDayVector()));
        System.out.println("User posts Month " + Arrays.toString(users.get(0).getClassifiedMonthVector()));
        System.out.println("User posts Day Of Month " + Arrays.toString(users.get(0).getClassifiedDayOfMonthVector()));
        List facUsers = fac.generateUserFirstActivityCluster(users);
        fac.writeFirstActivityCluster(facUsers);

       // *************************************************************************************
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
