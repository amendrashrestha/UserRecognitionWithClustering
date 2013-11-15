package com.post.main;

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

public class FileReaderMain {

    public static void main(String[] args) throws FileNotFoundException, IOException {
        UserDivision userDivision = new UserDivision();
        IOReadWrite ioReadWrite = new IOReadWrite();
        List a = ioReadWrite.getAllUsersAsObject();
        System.out.println("Number of users having post more than 60 messages: " + a.size());
        User user = new User();
        FirstActivityCluster fac = new FirstActivityCluster();
        //List testList = userDivision.divideFirstUser(a);
        List testList = userDivision.divideAllUser(a);
        user.setCategorizedTimeToUser(testList);
        testList = user.generateUserFirstActivityCluster(testList);
        for (int i = 0; i < testList.size(); i++) {
            User u = (User) testList.get(i);
            List postList = u.getUserPost();
            System.out.println("*************************************************");
            System.out.println("THIS IS A NEW USER:    " + String.valueOf(u.getId()));
            System.out.println("*************************************************");
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
    }
}
