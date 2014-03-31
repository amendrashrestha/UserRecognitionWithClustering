/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uni.post.utilities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import uni.cluster.IOHandler.IOReadWrite;
import uni.post.parser.controller.CopyFileContent;

/**
 *
 * @author ITE
 */
public class CountUser {

    public void getCommonUsers() {
        IOReadWrite io = new IOReadWrite();
        String CURRENT_USER_FILE_PATH = "C:\\Users\\ITE\\Downloads\\1998\\UsersPost\\";
        String NEXT_USER_FILE_PATH = "C:\\Users\\ITE\\Downloads\\1999\\UsersPost\\";
        List newUserDirectory = io.getAllDirectories(CURRENT_USER_FILE_PATH);
        Collections.sort(newUserDirectory, new CopyFileContent.ComparatorImpl());
        List combinedUserDirectory = io.getAllDirectories(NEXT_USER_FILE_PATH);
        Collections.sort(combinedUserDirectory, new CopyFileContent.ComparatorImpl());
        
        List matchedUsers = new ArrayList();
        
        for (int i = 0; i < newUserDirectory.size(); i++) {
            List newUsers = io.getAllFilesInADirectory(CURRENT_USER_FILE_PATH + "\\" + newUserDirectory.get(i).toString());
            List combinedUsers = io.getAllFilesInADirectory(NEXT_USER_FILE_PATH + "\\" + combinedUserDirectory.get(i).toString());
            System.out.println("NU:" + newUsers);
            System.out.println("OU:" + combinedUsers);

            for (int j = 0; j < newUsers.size(); j++) {
                String newUser = newUsers.get(j).toString();
                System.out.println("J User: " + newUser + "**");
                for (int k = 0; k < combinedUsers.size(); k++) {
                    // add users content if it exists                    
                    String oldUser = combinedUsers.get(k).toString();
                    //System.out.println("K User: " + oldUser);

                    if (newUser.equals(oldUser)) {
                        //System.out.println("Matched Users :" + oldUser);
                        matchedUsers.add(newUser);
                        break;
                    }
                }
                System.out.println("-----------------");
            }
            System.out.println("Total Users:" + combinedUsers);
            System.out.println("Old Users:" + newUsers);
            System.out.println("*******************");
        }
        System.out.println("Matched User Size: " + matchedUsers);
    }
}
