/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.user.cluster.main;

import com.user.cluster.IOHandler.IOReadWrite;
import com.user.cluster.clustering.FirstActivityCluster;
import com.user.cluster.clustering.UserDivision;
import com.user.cluster.parser.model.Posts;
import com.user.cluster.parser.model.ReturnSortedUserList;
import com.user.cluster.parser.model.User;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author ITE
 */
public class FirstActivityMain {

    public static void main(String[] args) throws FileNotFoundException, IOException {
        UserDivision userDivision = new UserDivision();
        IOReadWrite ioReadWrite = new IOReadWrite();
        User user = new User();
        FirstActivityCluster fac = new FirstActivityCluster();
        List<User> unDividedUserList = ioReadWrite.getAllUsersAsObject();
        System.out.println("Users:");
        for(User tempUser : unDividedUserList){
            System.out.println(tempUser.getId());
        }

         //For passing limited number of sorted users  
        /*Collections.sort(unDividedUserList, new ReturnSortedUserList());
        List<User> tempUsers = new ArrayList();      
        tempUsers = unDividedUserList.subList(0, 500);
         for(User tempUser : tempUsers){
             System.out.println(tempUser.getId());
         }
        List dividedUserList = userDivision.divideAllUser(tempUsers);*/
       
        

        List dividedUserList = userDivision.divideAllUser(unDividedUserList);
        dividedUserList = user.setCategorizedTimeToUser(dividedUserList);
        dividedUserList = user.generateUserFirstActivityCluster(dividedUserList);
        List facList = fac.getUserInSameClusterForFirstActivityClusterAsFACObject(dividedUserList);

        // This function writes the firstActivityCluster into file
        fac.writeFirstActivityCluster(facList);


        for (int i = 0; i < facList.size(); i++) {
            fac = (FirstActivityCluster) facList.get(i);
            System.out.println("The id of the user is:  " + fac.getUserID());
            int[] timeVec = fac.getPostTimeVector();
            System.out.println("The time vector is:  " + String.valueOf(timeVec[0]) + " " + String.valueOf(timeVec[1]) + " " + String.valueOf(timeVec[2]) + " "
                    + String.valueOf(timeVec[3]) + " " + String.valueOf(timeVec[4]) + " " + String.valueOf(timeVec[5]));
        }

        System.out.println("The number of users before first Activity Cluster is: " + String.valueOf(unDividedUserList.size()));
        System.out.println("The number of users in first Activity Cluster is: " + String.valueOf(facList.size()));
    }

}
