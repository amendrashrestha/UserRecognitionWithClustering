/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.post.main;

import com.post.parser.IOHandler.IOReadWrite;
import com.post.parser.clustering.FirstActivityCluster;
import com.post.parser.clustering.UserDivision;
import com.post.parser.model.Posts;
import com.post.parser.model.User;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Batman
 */
public class FirstActivityMain {

    public static void main(String[] args) throws FileNotFoundException, IOException {
        UserDivision userDivision = new UserDivision();
        IOReadWrite ioReadWrite = new IOReadWrite();
        User user = new User();
        FirstActivityCluster fac = new FirstActivityCluster();
        List tempUnDividedUserList = ioReadWrite.getAllUsersAsObject();

        /* For passing limited number of users
         * Collections.sort(tempUnDividedUserList);
         List unDividedUserList = new ArrayList();
         for(int i = 0; i < 5; i++){
         unDividedUserList.add(tempUnDividedUserList.get(i));
         } */

        List dividedUserList = userDivision.divideAllUser(tempUnDividedUserList);
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

        System.out.println("The number of users before first Activity Cluster is: " + String.valueOf(tempUnDividedUserList.size()));
        System.out.println("The number of users in first Activity Cluster is: " + String.valueOf(facList.size()));
    }
}
