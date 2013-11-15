/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.test.main;

import com.post.parser.IOHandler.IOReadWrite;
import com.post.parser.clustering.FirstActivityCluster;
import com.post.parser.clustering.UserDivision;
import com.post.parser.model.Posts;
import com.post.parser.model.User;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author Batman
 */
public class FirstActivityMain {
    
    public static void main(String[] args) throws FileNotFoundException, IOException{
        UserDivision userDivision = new UserDivision();
        IOReadWrite ioReadWrite = new IOReadWrite();
        User user = new User();
        FirstActivityCluster fac = new FirstActivityCluster();
        List unDividedUserList = ioReadWrite.getAllUsersAsObject();
        List dividedUserList = userDivision.divideAllUser(unDividedUserList);
        dividedUserList = user.setCategorizedTimeToUser(dividedUserList);
        dividedUserList = user.generateUserFirstActivityCluster(dividedUserList);
        List facList = fac.getUserInSameClusterForFirstActivityClusterAsFACObject(dividedUserList);
        
        // This function writes the firstActivityCluster into file
        fac.writeFirstActivityCluster(facList);
        
        System.out.println("The number of users in first Activity Cluster is: " + String.valueOf(facList.size()));
        for (int i=0; i< facList.size(); i++){
            fac = (FirstActivityCluster) facList.get(i);
            System.out.println("The id of the user is:  " + fac.getUserID());
            int[] timeVec = fac.getPostTimeVector();
            System.out.println("The time vector is:  " + String.valueOf(timeVec[0]) + " " + String.valueOf(timeVec[1]) + " " + String.valueOf(timeVec[2]) + " "
                    + String.valueOf(timeVec[3]) + " "+ String.valueOf(timeVec[4]) + " "+ String.valueOf(timeVec[5]));
        }
        
        /*
        for (int i = 0; i < dividedUserList.size(); i++) {
            User u = (User) dividedUserList.get(i);
            List postList = u.getUserPost();
            System.out.println("The User id is: " + String.valueOf(u.getId()));
            System.out.println("The size of Post is : " + postList.size());
            System.out.println("The type of User is: " + u.getType());
            int[] b = u.getClassifiedTimeVector();
            int[] c = u.getFirstActivityVector();
            System.out.println(String.valueOf(b[0])+ " " + String.valueOf(b[1]) + " " +String.valueOf(b[2]) + " " + String.valueOf(b[3])
                    + " " + String.valueOf(b[4]) + " " + String.valueOf(b[5]) );
            System.out.println(String.valueOf(c[0])+ " " + String.valueOf(c[1]) + " " +String.valueOf(c[2]) + " " + String.valueOf(c[3])
                    + " " + String.valueOf(c[4]) + " " + String.valueOf(c[5]) );
            for (int j = 0; j < postList.size(); j++) {
                Posts p = (Posts) postList.get(j);
                System.out.println("The " + j + "Post is: ");
                System.out.println("The time is: " + p.getTime());
                System.out.println("The conetent is: " + p.getContent());
            }
        }
        * */
    }
    
}
